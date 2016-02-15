package edu.galileo.android.photoshare.login.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.photoshare.PhotoShareAppModule;
import edu.galileo.android.photoshare.lib.di.LibsModule;
import edu.galileo.android.photoshare.login.ui.LoginActivity;

/**
 * Created by ykro.
 */
@Singleton
@Component(modules = {LoginModule.class, LibsModule.class, PhotoShareAppModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
