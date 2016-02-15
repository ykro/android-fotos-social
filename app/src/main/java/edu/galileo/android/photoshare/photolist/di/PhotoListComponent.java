package edu.galileo.android.photoshare.photolist.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.photoshare.PhotoShareAppModule;
import edu.galileo.android.photoshare.lib.di.LibsModule;
import edu.galileo.android.photoshare.photolist.ui.PhotoListFragment;

/**
 * Created by ykro.
 */
@Singleton
@Component(modules = {PhotoListModule.class, LibsModule.class, PhotoShareAppModule.class})
public interface PhotoListComponent {
    void inject(PhotoListFragment fragment);
}
