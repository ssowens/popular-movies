package com.ssowens.android.popularmovies.data;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.ssowens.android.popularmovies.MovieItem;

/**
 * Created by Sheila Owens on 2/26/18.
 */

public class FavoritesCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper and read from the database.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public FavoritesCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public MovieItem getFavoriteMovie() {

        long id = getLong(getColumnIndex(FavoriteMovieSchema.FavoriteMovieEntry
                .COLUMN_MOVIE_ID));
        String poster_path = getString(getColumnIndex(FavoriteMovieSchema.FavoriteMovieEntry
                .COLUMN_POSTER_PATH));

        MovieItem fav = new MovieItem(id);
        fav.setPoster_path(poster_path);

        return fav;
    }
}
