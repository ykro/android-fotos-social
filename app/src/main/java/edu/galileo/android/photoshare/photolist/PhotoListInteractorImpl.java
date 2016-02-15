package edu.galileo.android.photoshare.photolist;

import edu.galileo.android.photoshare.entities.Photo;

/**
 * Created by ykro.
 */
public class PhotoListInteractorImpl implements PhotoListInteractor {

    PhotoListRepository repository;

    public PhotoListInteractorImpl(PhotoListRepository repository) {
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
