package edu.galileo.android.photoshare;

import android.app.Application;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.firebase.client.Firebase;

/**
 * Created by ykro.
 */
public class PhotoShareApp extends Application {
    Cloudinary cloudinary;

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initCloudinary();
    }

    private void initCloudinary() {
        cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(getApplicationContext()));
    }

    private void initFirebase() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
