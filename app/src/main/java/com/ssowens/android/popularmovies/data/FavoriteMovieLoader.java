package com.ssowens.android.popularmovies.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.ssowens.android.popularmovies.MovieItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheila Owens on 2/28/18.
 */

public class FavoriteMovieLoader {

    private static final String TAG = FavoriteMovieLoader.class.getSimpleName();
    private Context context;
    private final SQLiteDatabase db;
    private View view;

    public FavoriteMovieLoader(Context context, View view) {
        this.context = context.getApplicationContext();
        this.db = new FavoriteMovieDbHelper(context).getWritableDatabase();
        this.view = view;
    }

    public List<MovieItem> getFavoriteMovies() {
        List<MovieItem> favoriteMovieList = new ArrayList<>();

        FavoritesCursorWrapper cursor = queryFavoriteMovies();
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                favoriteMovieList.add(cursor.getFavoriteMovie());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return favoriteMovieList;
    }

    public FavoritesCursorWrapper queryFavoriteMovies() {
        Cursor cursor = db.query(
                FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        return new FavoritesCursorWrapper(cursor);
    }

}

