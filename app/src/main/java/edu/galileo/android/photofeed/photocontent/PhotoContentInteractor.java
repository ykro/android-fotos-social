package edu.galileo.android.photofeed.photocontent;

import edu.galileo.android.photofeed.entities.Photo;

/**
 * Created by ykro.
 */
public interface PhotoContentInteractor {
    void subscribe();
    void unsubscribe();
    void removePhoto(Photo photo);
}
