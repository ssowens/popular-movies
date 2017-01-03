package com.ssowens.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MovieDetailActivity extends SingleFragmentActivity {

    public final static String TAG = "MovieDetailActivity";

    private static final String EXTRA_MOVIE_URL =
            "com.ssowens.android.popularmovies.movie_url";
    private static final String EXTRA_MOVIE_TITLE =
            "com.ssowens.android.popularmovies.movie_title";
    private static final String EXTRA_MOVIE_RELEASE_DATE =
            "com.ssowens.android.popularmovies.movie_release_date";
    private static final String EXTRA_VOTE_AVERAGE =
            "com.ssowens.android.popularmovies.movie_vote_average";
    private static final String EXTRA_MOVIE_OVERVIEW =
            "com.ssowens.android.popularmovies.movie_overview";

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
        String movieUrl = (String) getIntent()
                .getSerializableExtra(EXTRA_MOVIE_URL);
        String movieTitle = (String) getIntent()
                .getSerializableExtra(EXTRA_MOVIE_TITLE);
        String releaseDate = (String) getIntent()
                .getSerializableExtra(EXTRA_MOVIE_RELEASE_DATE);
        String voteAverage = (String) getIntent()
                .getSerializableExtra(EXTRA_VOTE_AVERAGE);
        String overview = (String) getIntent()
                .getSerializableExtra(EXTRA_MOVIE_OVERVIEW);

        return MovieDetailFragment.newInstance(movieUrl,
                movieTitle,
                releaseDate,
                voteAverage,
                overview);
    }

    public static Intent newIntent(Context packageContext,
                                   String imageUrl,
                                   String movieTitle,
                                   String releaseDate,
                                   String voteAverage,
                                   String overview) {
        Intent intent = new Intent(packageContext, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_URL, imageUrl);
        intent.putExtra(EXTRA_MOVIE_TITLE, movieTitle);
        intent.putExtra(EXTRA_MOVIE_RELEASE_DATE, releaseDate);
        intent.putExtra(EXTRA_VOTE_AVERAGE, voteAverage);
        intent.putExtra(EXTRA_MOVIE_OVERVIEW, overview);
        return intent;
    }
}