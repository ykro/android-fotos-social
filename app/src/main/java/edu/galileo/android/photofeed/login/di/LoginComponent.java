package edu.galileo.android.photofeed.login.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.photofeed.PhotoShareAppModule;
import edu.galileo.android.photofeed.lib.di.LibsModule;
import edu.galileo.android.photofeed.login.ui.LoginActivity;

/**
 * Created by ykro.
 */
@Singleton
@Component(modules = {LoginModule.class, LibsModule.class, PhotoShareAppModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
