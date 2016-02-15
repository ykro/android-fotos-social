package edu.galileo.android.photoshare.photolist.di;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.photoshare.entities.Photo;
import edu.galileo.android.photoshare.lib.base.BaseUtil;
import edu.galileo.android.photoshare.lib.base.EventBus;
import edu.galileo.android.photoshare.lib.base.ImageLoader;
import edu.galileo.android.photoshare.photolist.PhotoListInteractor;
import edu.galileo.android.photoshare.photolist.PhotoListInteractorImpl;
import edu.galileo.android.photoshare.photolist.PhotoListPresenter;
import edu.galileo.android.photoshare.photolist.PhotoListPresenterImpl;
import edu.galileo.android.photoshare.photolist.PhotoListRepository;
import edu.galileo.android.photoshare.photolist.PhotoListRepositoryImpl;
import edu.galileo.android.photoshare.photolist.ui.PhotoListView;
import edu.galileo.android.photoshare.photolist.ui.adapters.OnItemClickListener;
import edu.galileo.android.photoshare.photolist.ui.adapters.PhotoListAdapter;

/**
 * Created by ykro.
 */
@Module
public class PhotoListModule {
    PhotoListView view;
    OnItemClickListener onItemClickListener;

    public PhotoListModule(PhotoListView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides @Singleton
    PhotoListView providesPhotoListView() {
        return this.view;
    }

    @Provides @Singleton
    PhotoListPresenter providesPhotoListPresenter(EventBus eventBus, PhotoListView view, PhotoListInteractor listInteractor) {
        return new PhotoListPresenterImpl(eventBus, view, listInteractor);
    }

    @Provides @Singleton
    PhotoListInteractor providesPhotoListInteractor(PhotoListRepository repository) {
        return new PhotoListInteractorImpl(repository);
    }

    @Provides @Singleton
    PhotoListRepository providesPhotoListRepository(Firebase firebase, EventBus eventBus) {
        return new PhotoListRepositoryImpl(firebase, eventBus);
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
