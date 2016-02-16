package edu.galileo.android.photofeed.photocontent;

import edu.galileo.android.photofeed.entities.Photo;
import edu.galileo.android.photofeed.photocontent.events.PhotoContentEvent;

/**
 * Created by ykro.
 */
public interface PhotoContentPresenter {
    void onCreate();
    void onDestroy();

    void subscribe();
    void unsubscribe();

    void removePhoto(Photo photo);
    void onEventMainThread(PhotoContentEvent event);
}
