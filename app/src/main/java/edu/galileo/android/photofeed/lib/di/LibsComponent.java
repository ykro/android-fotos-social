package edu.galileo.android.photofeed.lib.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.photofeed.PhotoShareAppModule;

/**
 * Created by ykro.
 */
@Singleton
@Component(modules = {LibsModule.class, PhotoShareAppModule.class})
public interface LibsComponent {
}
