package edu.galileo.android.photoshare.main;

import android.location.Location;

/**
 * Created by ykro.
 */
public interface UploadInteractor {
    void execute(Location location, String path);
}
