package edu.galileo.android.photofeed.photocontent;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import edu.galileo.android.photofeed.entities.Photo;
import edu.galileo.android.photofeed.lib.base.EventBus;
import edu.galileo.android.photofeed.photocontent.events.PhotoContentEvent;

/**
 * Created by ykro.
 */
public class PhotoContentRepositoryImpl implements PhotoContentRepository {
    private Firebase firebase;
    private EventBus eventBus;
    private ChildEventListener photosEventListener;

    public PhotoContentRepositoryImpl(Firebase firebase, EventBus eventBus) {
        this.firebase = firebase;
        this.eventBus = eventBus;
    }

    @Override
    public void subscribe() {
        if (photosEventListener == null) {
            photosEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Photo photo = dataSnapshot.getValue(Photo.class);
                    photo.setId(dataSnapshot.getKey());

                    Map<String, Object> providerData = firebase.getAuth().getProviderData();
                    String email = providerData.get("email").toString();

                    boolean publishedByMy = photo.getEmail().equals(email);
                    photo.setPublishedByMe(publishedByMy);

                    post(PhotoContentEvent.READ_EVENT, photo);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Photo photo = dataSnapshot.getValue(Photo.class);
                    post(PhotoContentEvent.DELETE_EVENT, photo);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    post(firebaseError.getMessage());
                }
            };
        }
        firebase.addChildEventListener(photosEventListener);
    }

    @Override
    public void unsubscribe() {
        firebase.removeEventListener(photosEventListener);
    }

    @Override
    public void remove(Photo photo) {
        firebase.child(photo.getId()).removeValue();
        post(PhotoContentEvent.DELETE_EVENT, photo);
    }

    private void post(int type, Photo photo){
        post(type, photo, null);
    }

    private void post(String error){
        post(0, null, error);
    }

    private void post(int type, Photo photo, String error){
        PhotoContentEvent event = new PhotoContentEvent();
        event.setType(type);
        event.setError(error);
        event.setPhoto(photo);
        eventBus.post(event);
    }
}
