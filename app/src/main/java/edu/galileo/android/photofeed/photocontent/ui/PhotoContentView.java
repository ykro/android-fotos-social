package edu.galileo.android.photofeed.photocontent.ui;

import edu.galileo.android.photofeed.entities.Photo;

/**
 * Created by ykro.
 */
public interface PhotoContentView {
    void addPhoto(Photo photo);
    void removePhoto(Photo photo);
    void onPhotosError(String error);

}
