package edu.galileo.android.photoshare.lib.base;

/**
 * Created by ykro.
 */
public interface ImageStorageFinishedListener {
    void onSuccess();
    void onError(String error);
}
