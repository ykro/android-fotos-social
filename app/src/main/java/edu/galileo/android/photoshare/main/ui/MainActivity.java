package edu.galileo.android.photoshare.main.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.photoshare.PhotoShareApp;
import edu.galileo.android.photoshare.R;
import edu.galileo.android.photoshare.login.ui.LoginActivity;
import edu.galileo.android.photoshare.main.MainPresenter;
import edu.galileo.android.photoshare.main.ui.adapters.MainSectionsPagerAdapter;
import edu.galileo.android.photoshare.photolist.ui.PhotoListFragment;
import edu.galileo.android.photoshare.photomap.PhotoMapFragment;

public class MainActivity extends AppCompatActivity implements MainView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tabs) TabLayout tabLayout;
    @Bind(R.id.viewPager) ViewPager viewPager;

    @Inject MainPresenter presenter;
    @Inject MainSectionsPagerAdapter adapter;
    @Inject SharedPreferences sharedPreferences;

    private String photoPath;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupInjection();
        setupNavigation();
        setupGoogleAPIClient();

        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    private void setupGoogleAPIClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        presenter.logout();
        sharedPreferences.edit().clear().commit();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setupNavigation() {
        setSupportActionBar(toolbar);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupInjection() {
        String[] titles = new String[]{ getString(R.string.main_title_list),
                                        getString(R.string.main_title_map)};

        Fragment[] fragments = new Fragment[]{ new PhotoListFragment(),
                                               new PhotoMapFragment()};

        PhotoShareApp app = (PhotoShareApp) getApplication();
        app.getMainComponent(this, getSupportFragmentManager(), fragments, titles).inject(this);
    }

    @OnClick(R.id.fab)
    public void takePicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                photoFile = File.createTempFile(imageFileName, ".jpg", storageDir);
                photoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                Snackbar.make(viewPager, R.string.main_error_dispatch_camera, Snackbar.LENGTH_SHORT).show();
            }

            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_PICTURE);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICTURE && resultCode == RESULT_OK) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            presenter.uploadPhoto(lastLocation, photoPath);
            addPicToGallery();
        }
    }

    private void addPicToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    @Override
    public void onUploadComplete() {
        Snackbar.make(viewPager, R.string.main_notice, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadError(String error) {
        Snackbar.make(viewPager, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Snackbar.make(viewPager, connectionResult.getErrorMessage(), Snackbar.LENGTH_SHORT).show();
    }
}
