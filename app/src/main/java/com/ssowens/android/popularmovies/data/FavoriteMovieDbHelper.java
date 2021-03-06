package com.ssowens.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.ssowens.android.popularmovies.data.FavoriteMovieContract.FavoriteMovieEntry;

/**
 * Created by Sheila Owens on 2/25/18.
 */

public class FavoriteMovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favoriteMovie.db";
    private static final String TAG = FavoriteMovieDbHelper.class.getSimpleName();

    /*
 * If you change the database schema, you must increment the database version or the onUpgrade
 * method will not be called.
 *
 * The reason DATABASE_VERSION starts at 3 is because Sunshine has been used in conjunction
 * with the Android course for a while now. Believe it or not, older versions of Sunshine
 * still exist out in the wild. If we started this DATABASE_VERSION off at 1, upgrading older
 * versions of Sunshine could cause everything to break. Although that is certainly a rare
 * use-case, we wanted to watch out for it and warn you what could happen if you mistakenly
 * version your databases.
 */
    private static final int DATABASE_VERSION = 1;

    public FavoriteMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_MOVIE_TABLE =
                "CREATE TABLE " + FavoriteMovieEntry.TABLE_NAME + " (" +
                        FavoriteMovieEntry.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY, " +
                        FavoriteMovieEntry.COLUMN_POSTER_PATH + " TEXT" + ");";
        db.execSQL(SQL_CREATE_FAVORITE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is for handling any updates to the database.
        // Currently it is empty because there is only one version of the database
        // for now.
    }
}
