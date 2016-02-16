package edu.galileo.android.photofeed.login;

/**
 * Created by ykro.
 */
public interface LoginRepository {
    void signUp(final String email, final String password);
    void signIn(String email, String password);
}
