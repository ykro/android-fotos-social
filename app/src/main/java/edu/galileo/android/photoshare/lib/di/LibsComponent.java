package edu.galileo.android.photoshare.lib.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.photoshare.PhotoShareAppModule;

/**
 * Created by ykro.
 */
@Singleton
@Component(modules = {LibsModule.class, PhotoShareAppModule.class})
public interface LibsComponent {
}
