package edu.galileo.android.photofeed.main;

import android.location.Location;

/**
 * Created by ykro.
 */
public interface MainRepository {
    void logout();
    void uploadPhoto(Location location, String path);
}
