package com.ssowens.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sheila Owens on 2/25/18.
 */

public class FavoriteMovieContract {

    public static final String AUTHORITY = "com.ssowens.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITE_MOVIES = "favoriteMovies";

    public static final class FavoriteMovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();

        public static final String TABLE_NAME = "favoriteMovies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_POSTER_PATH = "poster_path";


    }
 }
