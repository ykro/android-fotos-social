package edu.galileo.android.photoshare.main;

import android.location.Location;

/**
 * Created by ykro.
 */
public class UploadInteractorImpl implements UploadInteractor {
    private MainRepository repository;

    public UploadInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Location location, String path) {
        repository.uploadPhoto(location, path);
    }

}
