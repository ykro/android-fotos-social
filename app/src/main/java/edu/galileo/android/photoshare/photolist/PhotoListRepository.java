package edu.galileo.android.photoshare.photolist;

import edu.galileo.android.photoshare.entities.Photo;

/**
 * Created by ykro.
 */
public interface PhotoListRepository {
    void subscribe();
    void unsubscribe();
    void remove(Photo photo);
}
