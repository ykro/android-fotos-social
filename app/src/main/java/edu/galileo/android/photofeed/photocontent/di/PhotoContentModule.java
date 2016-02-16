package edu.galileo.android.photofeed.photocontent.di;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.photofeed.entities.Photo;
import edu.galileo.android.photofeed.lib.base.BaseUtil;
import edu.galileo.android.photofeed.lib.base.EventBus;
import edu.galileo.android.photofeed.lib.base.ImageLoader;
import edu.galileo.android.photofeed.photocontent.PhotoContentInteractor;
import edu.galileo.android.photofeed.photocontent.PhotoContentInteractorImpl;
import edu.galileo.android.photofeed.photocontent.PhotoContentPresenter;
import edu.galileo.android.photofeed.photocontent.PhotoContentPresenterImpl;
import edu.galileo.android.photofeed.photocontent.PhotoContentRepository;
import edu.galileo.android.photofeed.photocontent.PhotoContentRepositoryImpl;
import edu.galileo.android.photofeed.photocontent.ui.PhotoContentView;
import edu.galileo.android.photofeed.photolist.ui.adapters.OnItemClickListener;
import edu.galileo.android.photofeed.photolist.ui.adapters.PhotoListAdapter;

/**
 * Created by ykro.
 */
@Module
public class PhotoContentModule {
    PhotoContentView view;
    OnItemClickListener onItemClickListener;

    public PhotoContentModule(PhotoContentView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides @Singleton
    PhotoContentView providesPhotoContentView() {
        return this.view;
    }

    @Provides @Singleton
    PhotoContentPresenter providesPhotoContentPresenter(EventBus eventBus, PhotoContentView view, PhotoContentInteractor listInteractor) {
        return new PhotoContentPresenterImpl(eventBus, view, listInteractor);
    }

    @Provides @Singleton
    PhotoContentInteractor providesPhotoContentInteractor(PhotoContentRepository repository) {
        return new PhotoContentInteractorImpl(repository);
    }

    @Provides @Singleton
    PhotoContentRepository providesPhotoContentRepository(Firebase firebase, EventBus eventBus) {
        return new PhotoContentRepositoryImpl(firebase, eventBus);
    }

    @Provides @Singleton
    PhotoListAdapter providesPhotosAdapter(BaseUtil utils, List<Photo> photoList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
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
