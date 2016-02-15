package edu.galileo.android.photoshare.photolist;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import edu.galileo.android.photoshare.entities.Photo;
import edu.galileo.android.photoshare.lib.base.EventBus;
import edu.galileo.android.photoshare.photolist.events.PhotoListEvent;

/**
 * Created by ykro.
 */
public class PhotoListRepositoryImpl implements PhotoListRepository {
    private Firebase firebase;
    private EventBus eventBus;
    private ChildEventListener photosEventListener;

    public PhotoListRepositoryImpl(Firebase firebase, EventBus eventBus) {
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
                    post(PhotoListEvent.READ_EVENT, photo);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Photo photo = dataSnapshot.getValue(Photo.class);
                    post(PhotoListEvent.DELETE_EVENT, photo);
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
        post(PhotoListEvent.DELETE_EVENT, photo);
    }

    private void post(int type, Photo photo){
        post(type, photo, null);
    }

    private void post(String error){
        post(0, null, error);
    }

    private void post(int type, Photo photo, String error){
        PhotoListEvent event = new PhotoListEvent();
        event.setType(type);
        event.setError(error);
        event.setPhoto(photo);
        eventBus.post(event);
    }
}
