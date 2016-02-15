package edu.galileo.android.photoshare.login.di;

import com.firebase.client.Firebase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.photoshare.lib.base.EventBus;
import edu.galileo.android.photoshare.login.LoginInteractor;
import edu.galileo.android.photoshare.login.LoginInteractorImpl;
import edu.galileo.android.photoshare.login.LoginPresenter;
import edu.galileo.android.photoshare.login.LoginPresenterImpl;
import edu.galileo.android.photoshare.login.LoginRepository;
import edu.galileo.android.photoshare.login.LoginRepositoryImpl;
import edu.galileo.android.photoshare.login.SignupInteractor;
import edu.galileo.android.photoshare.login.SignupInteractorImpl;
import edu.galileo.android.photoshare.login.ui.LoginView;

/**
 * Created by ykro.
 */
@Module
public class LoginModule {
    LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides @Singleton
    LoginView providesLoginView() {
        return this.view;
    }

    @Provides @Singleton
    LoginPresenter providesLoginPresenter(EventBus eventBus, LoginView loginView, LoginInteractor loginInteractor, SignupInteractor signupInteractor) {
        return new LoginPresenterImpl(eventBus, loginView, loginInteractor, signupInteractor);
    }

    @Provides @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository repository) {
        return new LoginInteractorImpl(repository);
    }

    @Provides @Singleton
    SignupInteractor providesSignupInteractor(LoginRepository repository) {
        return new SignupInteractorImpl(repository);
    }

    @Provides @Singleton
    LoginRepository providesLoginRepository(Firebase firebase, EventBus eventBus) {
        return new LoginRepositoryImpl(firebase, eventBus);
    }
}
