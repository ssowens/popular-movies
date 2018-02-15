package com.ssowens.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class MovieDetailActivity extends SingleFragmentActivity {

    public final static String TAG = "MovieDetailActivity";

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

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
    private static final String EXTRA_MOVIE_TRAILER =
            "com.ssowens.android.popularmovies.videos";
    private static final String EXTRA_TRAILER_KEY =
            "com.ssowens.android.popularmovies.key";
    private static final String EXTRA_MOVIE_ID =
            "com.ssowens.android.poputlarmovies.id";


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
        String movieId = (String) getIntent()
                .getSerializableExtra(EXTRA_MOVIE_ID);
        String trailer = (String) getIntent()
                .getSerializableExtra(EXTRA_MOVIE_TRAILER);
        String key = (String) getIntent()
                .getSerializableExtra(EXTRA_TRAILER_KEY);

        return MovieDetailFragment.newInstance(movieUrl,
                movieTitle,
                releaseDate,
                voteAverage,
                overview,
                movieId,
                trailer,
                key);
    }

    public static Intent newIntent(Context packageContext,
                                   String imageUrl,
                                   String movieTitle,
                                   String releaseDate,
                                   String voteAverage,
                                   String overview,
                                   String movieId,
                                   String trailer,
                                   String key) {
        Intent intent = new Intent(packageContext, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_URL, imageUrl);
        intent.putExtra(EXTRA_MOVIE_TITLE, movieTitle);
        intent.putExtra(EXTRA_MOVIE_RELEASE_DATE, releaseDate);
        intent.putExtra(EXTRA_VOTE_AVERAGE, voteAverage);
        intent.putExtra(EXTRA_MOVIE_OVERVIEW, overview);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        intent.putExtra(EXTRA_MOVIE_TRAILER, trailer);
        intent.putExtra(EXTRA_TRAILER_KEY, key);
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
}
