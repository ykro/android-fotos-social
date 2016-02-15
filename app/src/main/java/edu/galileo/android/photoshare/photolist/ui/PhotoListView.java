package edu.galileo.android.photoshare.photolist.ui;

import edu.galileo.android.photoshare.entities.Photo;

/**
 * Created by ykro.
 */
public interface PhotoListView {
    void addPhoto(Photo photos);
    void removePhoto(Photo photo);
    void onPhotosError(String error);
}
