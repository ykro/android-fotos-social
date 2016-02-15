package edu.galileo.android.photoshare.login;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import edu.galileo.android.photoshare.lib.base.EventBus;
import edu.galileo.android.photoshare.login.events.LoginEvent;

/**
 * Created by ykro.
 */
public class LoginRepositoryImpl implements LoginRepository {
    private Firebase firebase;
    private EventBus eventBus;

    public LoginRepositoryImpl(Firebase firebase, EventBus eventBus) {
        this.firebase = firebase;
        this.eventBus = eventBus;
    }

    @Override
    public void signUp(final String email, final String password) {
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                post(LoginEvent.onSignUpSuccess);
                signIn(email, password);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                post(LoginEvent.onSignUpError, firebaseError.getMessage());
            }
        });
    }

    @Override
    public void signIn(String email, String password) {
        if (email != null && password != null) {
            firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    Map<String, Object> providerData = authData.getProviderData();
                    String email = providerData.get("email").toString();
                    post(LoginEvent.onSignInSuccess, null, email);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    post(LoginEvent.onSignInError, firebaseError.getMessage());
                }
            });
        } else {
            checkAlreadyAuthenticated();
        }
    }

    public void checkAlreadyAuthenticated() {
        AuthData authData = firebase.getAuth();
        if (firebase.getAuth() != null) {
            Map<String, Object> providerData = authData.getProviderData();
            String email = providerData.get("email").toString();
            post(LoginEvent.onSignInSuccess, null, email);

        } else {
            post(LoginEvent.onFailedToRecoverSession);
        }
    }

    private void post(int type) {
        post(type, null);
    }

    private void post(int type, String errorMessage) {
        post(type, errorMessage, null);
    }

    private void post(int type, String errorMessage, String loggedUserEmail) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        loginEvent.setErrorMesage(errorMessage);
        loginEvent.setLoggedUserEmail(loggedUserEmail);
        eventBus.post(loginEvent);
    }

}
