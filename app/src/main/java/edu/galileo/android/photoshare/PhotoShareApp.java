package edu.galileo.android.photoshare;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.firebase.client.Firebase;

import edu.galileo.android.photoshare.lib.di.LibsModule;
import edu.galileo.android.photoshare.login.di.DaggerLoginComponent;
import edu.galileo.android.photoshare.login.di.LoginComponent;
import edu.galileo.android.photoshare.login.di.LoginModule;
import edu.galileo.android.photoshare.login.ui.LoginView;
import edu.galileo.android.photoshare.main.di.DaggerMainComponent;
import edu.galileo.android.photoshare.main.di.MainComponent;
import edu.galileo.android.photoshare.main.di.MainModule;
import edu.galileo.android.photoshare.main.ui.MainView;
import edu.galileo.android.photoshare.photolist.di.DaggerPhotoListComponent;
import edu.galileo.android.photoshare.photolist.di.PhotoListComponent;
import edu.galileo.android.photoshare.photolist.di.PhotoListModule;
import edu.galileo.android.photoshare.photolist.ui.PhotoListView;
import edu.galileo.android.photoshare.photolist.ui.adapters.OnItemClickListener;

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

    public PhotoListComponent getPhotoListComponent(Fragment fragment, PhotoListView view, OnItemClickListener onItemClickListener) {
        return DaggerPhotoListComponent
                .builder()
                .photoShareAppModule(getPhotoShareAppModule())
                .libsModule(getLibsModule(fragment))
                .photoListModule(new PhotoListModule(view, onItemClickListener))
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
