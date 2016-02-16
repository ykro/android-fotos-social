package edu.galileo.android.photofeed.main;

import android.location.Location;

import com.firebase.client.Firebase;

import java.io.File;
import java.util.Map;

import edu.galileo.android.photofeed.entities.Photo;
import edu.galileo.android.photofeed.lib.base.EventBus;
import edu.galileo.android.photofeed.lib.base.ImageStorage;
import edu.galileo.android.photofeed.lib.base.ImageStorageFinishedListener;
import edu.galileo.android.photofeed.main.events.MainEvent;

/**
 * Created by ykro.
 */
public class MainRepositoryImpl implements MainRepository {
    private EventBus eventBus;
    private Firebase firebase;
    private ImageStorage imageStorage;

    public MainRepositoryImpl(EventBus eventBus, Firebase firebase, ImageStorage imageStorage) {
        this.eventBus = eventBus;
        this.firebase = firebase;
        this.imageStorage = imageStorage;
    }

    @Override
    public void logout() {
        firebase.unauth();
    }

    @Override
    public void uploadPhoto(Location location, String path) {
        final Firebase firebasePhoto = firebase.push();
        final Photo photo = new Photo();

        Map<String, Object> providerData = firebase.getAuth().getProviderData();
        String email = providerData.get("email").toString();

        photo.setEmail(email);
        if (location != null) {
            photo.setLatitutde(location.getLatitude());
            photo.setLongitude(location.getLongitude());
        }
        photo.setId(firebasePhoto.getKey());

        post(MainEvent.UPLOAD_INIT);
        imageStorage.upload(new File(path), photo.getId(), new ImageStorageFinishedListener(){

            @Override
            public void onSuccess() {
                String url = imageStorage.getImageUrl(photo.getId());
                photo.setUrl(url);
                firebasePhoto.setValue(photo);

                post(MainEvent.UPLOAD_COMPLETE);
            }

            @Override
            public void onError(String error) {
                post(MainEvent.UPLOAD_ERROR, error);
            }
        });
    }

    private void post(int type){
        post(type, null);
    }

    private void post(int type, String error){
        MainEvent event = new MainEvent();
        event.setType(type);
        event.setError(error);
        eventBus.post(event);
    }
}
