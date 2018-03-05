package com.ssowens.android.popularmovies.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.ssowens.android.popularmovies.MovieDetailFragment;
import com.ssowens.android.popularmovies.MovieItem;
import com.ssowens.android.popularmovies.R;
import com.ssowens.android.popularmovies.data.FavoriteMovieContract;

public class MovieDetailActivity extends SingleFragmentActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public final static String TAG = "MovieDetailActivity";

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final String EXTRA_MOVIE_ITEM =
            "com.ssowens.android.popularmovies.movie_item";
    private static final int MOVIE_LOADER_ID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        // Set this option for defaults in the Settings screen
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu()");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Fragment createFragment() {

        MovieItem item = (MovieItem) getIntent().getSerializableExtra(EXTRA_MOVIE_ITEM);

        return MovieDetailFragment.newInstance(item);
    }

    public static Intent newIntent(Context packageContext, MovieItem item) {
        Intent intent = new Intent(packageContext, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ITEM, item);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_START_STANDALONE_PLAYER && resultCode != RESULT_OK) {
            YouTubeInitializationResult errorReason =
                    YouTubeStandalonePlayer.getReturnedInitializationResult(data);
            if (errorReason.isUserRecoverableError()) {
                errorReason.getErrorDialog(this, 0).show();
            } else {
                String errorMessage =
                        String.format(getString(R.string.error_player), errorReason.toString());
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all favorite movies
        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor favMovieData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (favMovieData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(favMovieData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data
                try {
                    return getContentResolver().query(FavoriteMovieContract.FavoriteMovieEntry
                                    .CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                favMovieData = data;
                super.deliverResult(data);
            }
        };

    }

    //Called when a previously created loader has finished its load.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    // Called when a previously created loader is being reset
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
