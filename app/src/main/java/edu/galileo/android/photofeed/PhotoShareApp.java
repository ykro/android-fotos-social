package edu.galileo.android.photofeed;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.firebase.client.Firebase;

import edu.galileo.android.photofeed.lib.di.LibsModule;
import edu.galileo.android.photofeed.login.di.DaggerLoginComponent;
import edu.galileo.android.photofeed.login.di.LoginComponent;
import edu.galileo.android.photofeed.login.di.LoginModule;
import edu.galileo.android.photofeed.login.ui.LoginView;
import edu.galileo.android.photofeed.main.di.DaggerMainComponent;
import edu.galileo.android.photofeed.main.di.MainComponent;
import edu.galileo.android.photofeed.main.di.MainModule;
import edu.galileo.android.photofeed.main.ui.MainView;
import edu.galileo.android.photofeed.photocontent.di.DaggerPhotoContentComponent;
import edu.galileo.android.photofeed.photocontent.di.PhotoContentComponent;
import edu.galileo.android.photofeed.photocontent.di.PhotoContentModule;
import edu.galileo.android.photofeed.photocontent.ui.PhotoContentView;
import edu.galileo.android.photofeed.photolist.ui.adapters.OnItemClickListener;

/**
 * Created by ykro.
 */
public class PhotoShareApp extends Application {
    private final static String EMAIL_KEY = "email";

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
    }

    private void initFirebase() {
        Firebase.setAndroidContext(this);
    }

    public static String getEmailKey() {
        return EMAIL_KEY;
    }

    public PhotoContentComponent getPhotoContentComponent(Fragment fragment, PhotoContentView view, OnItemClickListener onItemClickListener) {
        return DaggerPhotoContentComponent
                .builder()
                .photoShareAppModule(getPhotoShareAppModule())
                .libsModule(getLibsModule(fragment))
                .photoContentModule(new PhotoContentModule(view, onItemClickListener))
                .build();

    }

    public MainComponent getMainComponent(MainView view, FragmentManager manager, Fragment[]fragments, String[] titles) {
        return DaggerMainComponent
                .builder()
                .photoShareAppModule(getPhotoShareAppModule())
                .libsModule(getLibsModule())
                .mainModule(new MainModule(view, manager, fragments, titles))
                .build();
    }

    public LoginComponent getLoginComponent(LoginView view) {
        return DaggerLoginComponent
                .builder()
                .photoShareAppModule(getPhotoShareAppModule())
                .libsModule(getLibsModule())
                .loginModule(new LoginModule(view))
                .build();

    }

    private LibsModule getLibsModule(Fragment fragment){
        return new LibsModule(fragment);
    }

    private LibsModule getLibsModule(){
        return new LibsModule();
    }

    private PhotoShareAppModule getPhotoShareAppModule(){
        return new PhotoShareAppModule(this);
    }

}
