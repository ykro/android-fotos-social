package edu.galileo.android.photoshare.main.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.photoshare.PhotoShareAppModule;
import edu.galileo.android.photoshare.lib.di.LibsModule;
import edu.galileo.android.photoshare.main.ui.MainActivity;

/**
 * Created by ykro.
 */
@Singleton
@Component(modules = {MainModule.class, LibsModule.class, PhotoShareAppModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
