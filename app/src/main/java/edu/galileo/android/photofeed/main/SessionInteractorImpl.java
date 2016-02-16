package edu.galileo.android.photofeed.main;

/**
 * Created by ykro.
 */
public class SessionInteractorImpl implements SessionInteractor {
    MainRepository repository;

    public SessionInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void logout() {
        repository.logout();
    }
}
