package edu.galileo.android.photoshare.photolist;

import edu.galileo.android.photoshare.entities.Photo;
import edu.galileo.android.photoshare.photolist.events.PhotoListEvent;
import edu.galileo.android.photoshare.lib.base.EventBus;
import edu.galileo.android.photoshare.photolist.ui.PhotoListView;

/**
 * Created by ykro.
 */
public class PhotoListPresenterImpl implements PhotoListPresenter {
    EventBus eventBus;
    PhotoListView view;
    PhotoListInteractor interactor;

    public PhotoListPresenterImpl(EventBus eventBus, PhotoListView view, PhotoListInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.view = null;
        eventBus.unregister(this);
    }

    @Override
    public void subscribe() {
        interactor.subscribe();
    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }

    @Override
    public void removePhoto(Photo photo) {
        interactor.removePhoto(photo);
    }

    @Override
    public void onEventMainThread(PhotoListEvent event) {
        if (this.view != null) {
            String error = event.getError();
            if (error != null) {
                view.onPhotosError(error);
            } else {
                if (event.getType() == PhotoListEvent.READ_EVENT) {
                    view.addPhoto(event.getPhoto());
                } else if (event.getType() == PhotoListEvent.DELETE_EVENT) {
                    view.removePhoto(event.getPhoto());
                }
            }
        }
    }
}
