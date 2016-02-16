package edu.galileo.android.photofeed.photomap.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.photofeed.domain.FirebaseAPI;
import edu.galileo.android.photofeed.domain.Util;
import edu.galileo.android.photofeed.entities.Photo;
import edu.galileo.android.photofeed.lib.base.EventBus;
import edu.galileo.android.photofeed.lib.base.ImageLoader;
import edu.galileo.android.photofeed.photolist.ui.adapters.OnItemClickListener;
import edu.galileo.android.photofeed.photolist.ui.adapters.PhotoListAdapter;
import edu.galileo.android.photofeed.photomap.PhotoMapInteractor;
import edu.galileo.android.photofeed.photomap.PhotoMapInteractorImpl;
import edu.galileo.android.photofeed.photomap.PhotoMapPresenter;
import edu.galileo.android.photofeed.photomap.PhotoMapPresenterImpl;
import edu.galileo.android.photofeed.photomap.PhotoMapRepository;
import edu.galileo.android.photofeed.photomap.PhotoMapRepositoryImpl;
import edu.galileo.android.photofeed.photomap.ui.PhotoMapView;

/**
 * Created by ykro.
 */
@Module
public class PhotoMapModule {
    PhotoMapView view;
    OnItemClickListener onItemClickListener;

    public PhotoMapModule(PhotoMapView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides @Singleton
    PhotoMapView providesPhotoContentView() {
        return this.view;
    }

    @Provides @Singleton
    PhotoMapPresenter providesPhotoContentPresenter(EventBus eventBus, PhotoMapView view, PhotoMapInteractor listInteractor) {
        return new PhotoMapPresenterImpl(eventBus, view, listInteractor);
    }

    @Provides @Singleton
    PhotoMapInteractor providesPhotoContentInteractor(PhotoMapRepository repository) {
        return new PhotoMapInteractorImpl(repository);
    }

    @Provides @Singleton
    PhotoMapRepository providesPhotoContentRepository(FirebaseAPI firebase, EventBus eventBus) {
        return new PhotoMapRepositoryImpl(firebase, eventBus);
    }

    @Provides @Singleton
    PhotoListAdapter providesPhotosAdapter(Util utils, List<Photo> photoList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        return new PhotoListAdapter(utils, photoList, imageLoader, onItemClickListener);
    }

    @Provides @Singleton
    OnItemClickListener providesOnItemClickListener() {
        return this.onItemClickListener;
    }

    @Provides @Singleton
    List<Photo> providesPhotosList() {
        return new ArrayList<Photo>();
    }

}
