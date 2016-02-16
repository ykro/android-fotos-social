package edu.galileo.android.photofeed.photocontent;

import edu.galileo.android.photofeed.entities.Photo;
import edu.galileo.android.photofeed.lib.base.EventBus;
import edu.galileo.android.photofeed.photocontent.events.PhotoContentEvent;
import edu.galileo.android.photofeed.photocontent.ui.PhotoContentView;

/**
 * Created by ykro.
 */
public class PhotoContentPresenterImpl implements PhotoContentPresenter {
    EventBus eventBus;
    PhotoContentView view;
    PhotoContentInteractor interactor;

    public PhotoContentPresenterImpl(EventBus eventBus, PhotoContentView view, PhotoContentInteractor interactor) {
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
    public void onEventMainThread(PhotoContentEvent event) {
        if (this.view != null) {
            String error = event.getError();
            if (error != null) {
                view.onPhotosError(error);
            } else {
                if (event.getType() == PhotoContentEvent.READ_EVENT) {
                    view.addPhoto(event.getPhoto());
                } else if (event.getType() == PhotoContentEvent.DELETE_EVENT) {
                    view.removePhoto(event.getPhoto());
                }
            }
        }
    }
}
