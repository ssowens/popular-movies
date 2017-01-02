package com.ssowens.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class MovieDetailActivity extends SingleFragmentActivity {

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
