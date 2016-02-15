package edu.galileo.android.photoshare.lib.di;

import android.content.Context;
import android.location.Geocoder;
import android.support.v4.app.Fragment;

import com.firebase.client.Firebase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.photoshare.lib.CloudinaryImageStorage;
import edu.galileo.android.photoshare.lib.GlideImageLoader;
import edu.galileo.android.photoshare.lib.GreenRobotEventBus;
import edu.galileo.android.photoshare.lib.Util;
import edu.galileo.android.photoshare.lib.base.BaseUtil;
import edu.galileo.android.photoshare.lib.base.EventBus;
import edu.galileo.android.photoshare.lib.base.ImageLoader;
import edu.galileo.android.photoshare.lib.base.ImageStorage;

/**
 * Created by ykro.
 */
@Module
public class LibsModule {
    private Fragment fragment;
    private final static String FIREBASE_URL = "https://android-photo-share.firebaseio.com/";

    public LibsModule() {
    }

    public LibsModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Singleton
    EventBus providesEventBus() {
        return new GreenRobotEventBus();
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(Fragment fragment) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (fragment != null) {
            imageLoader.setLoaderContext(fragment);
        }
        return imageLoader;
    }

    @Provides
    @Singleton
    ImageStorage providesImageStorage(Context context, EventBus eventBus) {
        ImageStorage imageStorage = new CloudinaryImageStorage(context, eventBus);
        return imageStorage;
    }

    @Provides
    @Singleton
    Firebase providesFirebase() {
        return new Firebase(FIREBASE_URL);
    }

    @Provides
    @Singleton
    BaseUtil providesUtil(Geocoder geocoder) {
        return new Util(geocoder);
    }

    @Provides
    @Singleton
    Geocoder providesGeocoder(Context context) {
        return new Geocoder(context);
    }

    @Provides
    @Singleton
    Fragment providesFragment(){
        return this.fragment;
    }
}
