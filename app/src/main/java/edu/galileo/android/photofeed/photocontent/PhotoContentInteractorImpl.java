package edu.galileo.android.photofeed.photocontent;

import edu.galileo.android.photofeed.entities.Photo;

/**
 * Created by ykro.
 */
public class PhotoContentInteractorImpl implements PhotoContentInteractor {

    PhotoContentRepository repository;

    public PhotoContentInteractorImpl(PhotoContentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void removePhoto(Photo photo) {
        repository.remove(photo);
    }
}
