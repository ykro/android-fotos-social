package edu.galileo.android.photoshare.photolist;

import edu.galileo.android.photoshare.entities.Photo;

/**
 * Created by ykro.
 */
public interface PhotoListInteractor {
    void subscribe();
    void unsubscribe();
    void removePhoto(Photo photo);
}
