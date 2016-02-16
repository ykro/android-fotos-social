package edu.galileo.android.photofeed.photocontent.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.photofeed.PhotoShareAppModule;
import edu.galileo.android.photofeed.lib.di.LibsModule;
import edu.galileo.android.photofeed.photolist.ui.PhotoListFragment;
import edu.galileo.android.photofeed.photomap.PhotoMapFragment;

/**
 * Created by ykro.
 */
@Singleton
@Component(modules = {PhotoContentModule.class, LibsModule.class, PhotoShareAppModule.class})
public interface PhotoContentComponent {
    void inject(PhotoListFragment fragment);
    void inject(PhotoMapFragment fragment);
}
