package edu.galileo.android.photoshare.main;

import android.location.Location;

import com.firebase.client.Firebase;

import java.io.File;
import java.util.Map;

import edu.galileo.android.photoshare.entities.Photo;
import edu.galileo.android.photoshare.lib.base.EventBus;
import edu.galileo.android.photoshare.lib.base.ImageStorage;
import edu.galileo.android.photoshare.lib.base.ImageStorageFinishedListener;
import edu.galileo.android.photoshare.main.events.MainEvent;

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
        final Firebase newPhoto = firebase.push();
        final Photo photo = new Photo();

        Map<String, Object> providerData = firebase.getAuth().getProviderData();
        String email = providerData.get("email").toString();

        photo.setEmail(email);
        photo.setLatitutde(location.getLatitude());
        photo.setLongitude(location.getLongitude());
        photo.setId(newPhoto.getKey());

        imageStorage.upload(new File(path), photo.getId(), new ImageStorageFinishedListener(){

            @Override
            public void onSuccess() {
                String url = imageStorage.getImageUrl(photo.getId());
                photo.setUrl(url);
                newPhoto.setValue(photo);
                post();
            }

            @Override
            public void onError(String error) {
                post(error);
            }
        });
    }

    private void post(){
        post(null);
    }

    private void post(String error){
        MainEvent event = new MainEvent();
        event.setError(error);
        eventBus.post(event);
    }
}
