package edu.galileo.android.photofeed.photomap;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.galileo.android.photofeed.PhotoShareApp;
import edu.galileo.android.photofeed.R;
import edu.galileo.android.photofeed.entities.Photo;
import edu.galileo.android.photofeed.lib.base.BaseUtil;
import edu.galileo.android.photofeed.lib.base.ImageLoader;
import edu.galileo.android.photofeed.photocontent.PhotoContentPresenter;
import edu.galileo.android.photofeed.photocontent.ui.PhotoContentView;

public class PhotoMapFragment extends Fragment implements PhotoContentView, OnMapReadyCallback, GoogleMap.InfoWindowAdapter {
    @Bind(R.id.container)
    FrameLayout container;

    @Inject
    BaseUtil utils;

    @Inject
    ImageLoader imageLoader;

    @Inject
    PhotoContentPresenter presenter;

    private GoogleMap map;
    private HashMap<Photo, Marker> photos;

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

        photos = new HashMap<Photo, Marker>();

        presenter.onCreate();
        presenter.subscribe();
    }

    private void setupInjection() {
        PhotoShareApp app = (PhotoShareApp) getActivity().getApplication();
        app.getPhotoContentComponent(this, this, null).inject(this);
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
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
    }

    @Override
    public void addPhoto(Photo photo) {
        LatLng location = new LatLng(photo.getLatitutde(), photo.getLongitude());

        Marker marker = map.addMarker(new MarkerOptions().position(location));
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
        photos.put(photo, marker);
    }

    @Override
    public void removePhoto(Photo photo) {
        Marker marker = photos.get(photo);
        marker.remove();
        photos.remove(photo);
    }

    @Override
    public void onPhotosError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View window = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.info_window, null);

        for(Map.Entry<Photo, Marker> entry : photos.entrySet()) {
            Photo currentPhoto = entry.getKey();
            Marker currentMarker = entry.getValue();
            if (currentMarker.getId().equals(marker.getId())) {
                render(window, currentPhoto);
                break;
            }
        }

        return window;
    }

    private void render(View view, final Photo currentPhoto) {
        CircleImageView imgAvatar = (CircleImageView)view.findViewById(R.id.imgAvatar);
        TextView txtUser = (TextView)view.findViewById(R.id.txtUser);
        final ImageView imgMain = (ImageView)view.findViewById(R.id.imgMain);
        ImageButton btnShare = (ImageButton)view.findViewById(R.id.imgShare);
        ImageButton btnDelete = (ImageButton)view.findViewById(R.id.imgDelete);

        imageLoader.load(imgMain, currentPhoto.getUrl());
        imageLoader.load(imgAvatar, utils.getAvatarUrl(currentPhoto.getEmail()));
        txtUser.setText(currentPhoto.getEmail());

        if (currentPhoto.isPublishedByMe()){
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteClick(currentPhoto);
                }
            });
        } else {
            btnDelete.setVisibility(View.GONE);
        }

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShareClick(currentPhoto, imgMain);
            }
        });
    }

    private void onShareClick(Photo photo, ImageView img) {
        Bitmap bitmap = ((GlideBitmapDrawable)img.getDrawable()).getBitmap();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, null, null);
        Uri imageUri =  Uri.parse(path);

        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, getString(R.string.photolist_message_share)));
    }

    private void onDeleteClick(Photo photo) {
        presenter.removePhoto(photo);
    }
}
