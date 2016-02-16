package edu.galileo.android.photofeed.photomap.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.galileo.android.photofeed.PhotoFeedApp;
import edu.galileo.android.photofeed.R;
import edu.galileo.android.photofeed.domain.Util;
import edu.galileo.android.photofeed.entities.Photo;
import edu.galileo.android.photofeed.lib.base.ImageLoader;
import edu.galileo.android.photofeed.photomap.PhotoMapPresenter;

public class PhotoMapFragment extends Fragment implements PhotoMapView, OnMapReadyCallback, GoogleMap.InfoWindowAdapter {
    @Bind(R.id.container)
    FrameLayout container;

    @Inject
    Util utils;

    @Inject
    ImageLoader imageLoader;

    @Inject
    PhotoMapPresenter presenter;

    private GoogleMap map;
    private HashMap<Marker, Photo> markers;

    public PhotoMapFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();

        markers = new HashMap<Marker, Photo>();

        presenter.onCreate();
        presenter.subscribe();
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        presenter.onDestroy();
        super.onDestroy();
    }

    private void setupInjection() {
        PhotoFeedApp app = (PhotoFeedApp) getActivity().getApplication();
        app.getPhotoMapComponent(this, this, null).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setInfoWindowAdapter(this);
        map.setMyLocationEnabled(true);
    }

    @Override
    public void addPhoto(Photo photo) {
        LatLng location = new LatLng(photo.getLatitutde(), photo.getLongitude());

        Marker marker = map.addMarker(new MarkerOptions().position(location));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 6));
        markers.put(marker, photo);

    }

    @Override
    public void removePhoto(Photo photo) {
        for(Map.Entry<Marker, Photo> entry : markers.entrySet()) {
            Photo currentPhoto = entry.getValue();
            Marker currentMarker = entry.getKey();
            if (currentPhoto.getId().equals(photo.getId())) {
                currentMarker.remove();
                markers.remove(currentMarker);
                break;
            }
        }
    }

    @Override
    public void onPhotosError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public View getInfoContents(Marker marker) {
        View window = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.info_window, null);
        Photo photo = markers.get(marker);
        render(window, photo);

        return window;
    }

    private void render(View view, final Photo currentPhoto) {
        CircleImageView imgAvatar = (CircleImageView)view.findViewById(R.id.imgAvatar);
        TextView txtUser = (TextView)view.findViewById(R.id.txtUser);
        final ImageView imgMain = (ImageView)view.findViewById(R.id.imgMain);

        imageLoader.load(imgMain, currentPhoto.getUrl());
        imageLoader.load(imgAvatar, utils.getAvatarUrl(currentPhoto.getEmail()));
        txtUser.setText(currentPhoto.getEmail());
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
}
