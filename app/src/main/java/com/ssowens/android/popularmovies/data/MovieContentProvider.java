package com.ssowens.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.ssowens.android.popularmovies.MovieItem;
import com.ssowens.android.popularmovies.R;
import java.util.ArrayList;
import java.util.List;

import static com.ssowens.android.popularmovies.data.FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME;

/**
 * Created by Sheila Owens on 3/4/18.
 */

public class MovieContentProvider extends ContentProvider {

    private static final String TAG = MovieContentProvider.class.getSimpleName();
    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_ID = 101;
    private Context context;
    private SQLiteDatabase db;


    private FavoriteMovieDbHelper favoriteMovieDbHelper;

    // Make this a static variable that I can access throughout the provider code.
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Add matches for directory
        uriMatcher.addURI(FavoriteMovieContract.AUTHORITY, FavoriteMovieContract
                .PATH_FAVORITE_MOVIES, MOVIES);

        // Add matches for a single item
        uriMatcher.addURI(FavoriteMovieContract.AUTHORITY, FavoriteMovieContract
                .PATH_FAVORITE_MOVIES + "/#", MOVIES_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        favoriteMovieDbHelper = new FavoriteMovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = favoriteMovieDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        long id;

        switch (match) {
            case MOVIES:
                id = db.insert(TABLE_NAME, null, values);
                if (id > 0) {
                    //success
                    returnUri = ContentUris.withAppendedId(FavoriteMovieContract
                            .FavoriteMovieEntry.CONTENT_URI, id);
                } else {
                    Toast.makeText(getContext(), getContext().getString(R.string
                            .duplicate_favorite), Toast
                            .LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Notify resolver if the uri has been changed
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = favoriteMovieDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case MOVIES:
                retCursor = db.query(FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
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

        final SQLiteDatabase db = new FavoriteMovieDbHelper(getContext()).getReadableDatabase();
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
