package edu.galileo.android.photoshare.photolist.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.photoshare.PhotoShareApp;
import edu.galileo.android.photoshare.R;
import edu.galileo.android.photoshare.entities.Photo;
import edu.galileo.android.photoshare.photolist.PhotoListPresenter;
import edu.galileo.android.photoshare.photolist.ui.adapters.OnItemClickListener;
import edu.galileo.android.photoshare.photolist.ui.adapters.PhotoListAdapter;

public class PhotoListFragment extends Fragment implements PhotoListView, OnItemClickListener {
    @Bind(R.id.container) RelativeLayout container;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    @Inject
    PhotoListAdapter adapter;
    @Inject
    PhotoListPresenter presenter;
    
    public PhotoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();
        presenter.onCreate();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void setupInjection() {
        PhotoShareApp app = (PhotoShareApp) getActivity().getApplication();
        app.getPhotoListComponent(this, this, this).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    @Override
    public void onPhotosError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void addPhoto(Photo photo) {
        adapter.addPhoto(photo);
    }

    @Override
    public void removePhoto(Photo photo) {
        adapter.removePhoto(photo);
    }

    @Override
    public void onPlaceClick(Photo photo) {

    }

    @Override
    public void onShareClick(Photo photo) {

    }

    @Override
    public void onDeleteClick(Photo photo) {
        presenter.removePhoto(photo);
    }
}
