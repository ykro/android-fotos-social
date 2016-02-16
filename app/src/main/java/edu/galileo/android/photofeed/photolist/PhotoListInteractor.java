package edu.galileo.android.photofeed.photolist;

import edu.galileo.android.photofeed.entities.Photo;

/**
 * Created by ykro.
 */
public interface PhotoListInteractor {
    void subscribe();
    void unsubscribe();
    void removePhoto(Photo photo);
}
