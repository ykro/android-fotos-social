package edu.galileo.android.photoshare.photolist;

import edu.galileo.android.photoshare.entities.Photo;
import edu.galileo.android.photoshare.photolist.events.PhotoListEvent;

/**
 * Created by ykro.
 */
public interface PhotoListPresenter {
    void onCreate();
    void onDestroy();

    void subscribe();
    void unsubscribe();

    void removePhoto(Photo photo);
    void onEventMainThread(PhotoListEvent event);
}
