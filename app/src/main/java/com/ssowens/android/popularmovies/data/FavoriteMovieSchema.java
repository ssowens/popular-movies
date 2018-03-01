package com.ssowens.android.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by Sheila Owens on 2/25/18.
 */

public class FavoriteMovieSchema {

    public static final class FavoriteMovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favoriteMovies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_POSTER_PATH = "poster_path";
    }
 }
