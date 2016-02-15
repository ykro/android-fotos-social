package edu.galileo.android.photoshare.main;

import android.location.Location;

import edu.galileo.android.photoshare.lib.base.EventBus;
import edu.galileo.android.photoshare.main.events.MainEvent;
import edu.galileo.android.photoshare.main.ui.MainView;

/**
 * Created by ykro.
 */
public class MainPresenterImpl implements MainPresenter{
    MainView view;
    EventBus eventBus;
    UploadInteractor uploadInteractor;
    SessionInteractor sessionInteractor;

    public MainPresenterImpl(MainView view, EventBus eventBus, UploadInteractor uploadInteractor, SessionInteractor sessionInteractor) {
        this.view = view;
        this.eventBus = eventBus;
        this.uploadInteractor = uploadInteractor;
        this.sessionInteractor = sessionInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void uploadPhoto(Location location, String path) {
        uploadInteractor.execute(location, path);
    }

    @Override
    public void onEventMainThread(MainEvent event) {
        String error = event.getError();
        if (this.view != null) {
            if (error != null) {
                view.onUploadError(error);
            } else {
                view.onUploadComplete();
            }
        }
    }

    @Override
    public void logout() {
        sessionInteractor.logout();
    }
}
