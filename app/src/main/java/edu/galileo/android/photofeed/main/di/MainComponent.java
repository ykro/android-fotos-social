package edu.galileo.android.photofeed.main.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.photofeed.PhotoShareAppModule;
import edu.galileo.android.photofeed.lib.di.LibsModule;
import edu.galileo.android.photofeed.main.ui.MainActivity;

/**
 * Created by ykro.
 */
@Singleton
@Component(modules = {MainModule.class, LibsModule.class, PhotoShareAppModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
