package edu.galileo.android.photofeed.lib.base;

/**
 * Created by ykro.
 */
public interface ImageStorageFinishedListener {
    void onSuccess();
    void onError(String error);
}
