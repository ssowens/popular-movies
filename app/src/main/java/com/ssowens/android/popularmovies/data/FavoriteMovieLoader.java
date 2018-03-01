package com.ssowens.android.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.ssowens.android.popularmovies.MovieGridFragment;
import com.ssowens.android.popularmovies.MovieItem;
import com.ssowens.android.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheila Owens on 2/28/18.
 */

public class FavoriteMovieLoader {

    private static final String TAG = MovieGridFragment.class.getSimpleName();
    private static FavoriteMovieLoader sFavoriteMovieLoader;
    private Context context;
    private SQLiteDatabase db;
    private View view;
    private FavoriteMovieDbHelper dbHelper;

    public FavoriteMovieLoader(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    //TODO Need to be able to get all movies and a single movie
    public List<MovieItem> getFavoriteMovies() {
        Log.v(TAG, "Sheila getFavoriteMovies");
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

    public void addFavoriteMovies(MovieItem movieItem) {
        Log.v(TAG, "addFavoriteMovies");

        long newRowId = 0;

        dbHelper = new FavoriteMovieDbHelper(context);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //TODO add other fields
        contentValues.put(FavoriteMovieSchema.FavoriteMovieEntry.COLUMN_MOVIE_ID,
                movieItem.getMovieId());
        contentValues.put(FavoriteMovieSchema.FavoriteMovieEntry.COLUMN_POSTER_PATH,
                movieItem.getImageUrl());
        try {
            newRowId = db.insert(FavoriteMovieSchema.FavoriteMovieEntry.TABLE_NAME,
                    null, contentValues);
          //  db.close();
        } catch (SQLiteConstraintException e) {
            Log.i(TAG, "Error - " + e.toString());
        }
        if (newRowId != -1) {
            Snackbar.make(view, R.string.favorite_added, Snackbar
                    .LENGTH_SHORT).show();
        } else {
            Snackbar.make(view, R.string.duplicate_favorite,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    public void initializeDbForReading() {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
    }

    public FavoritesCursorWrapper queryFavoriteMovies() {
       // initializeDbForReading();
        Cursor cursor = dbHelper.getReadableDatabase().query(
                FavoriteMovieSchema.FavoriteMovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        return new FavoritesCursorWrapper(cursor);
    }

}

