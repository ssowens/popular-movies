package com.ssowens.android.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sheila Owens on 12/12/16.
 */

public class MovieFetchr {
    private static final String TAG = "MovieFetchr";

    private static final String TMDB_RESULTS = "results";
    private static final String TMDB_POSTER_PATH = "poster_path";
    private static final String TMDB_VOTE_AVERAGE = "vote_average";
    private static final String TMDB_OVERVIEW = "overview";
    private static final String TMDB_RELEASE_DATE = "release_date";
    private static final String TMDB_ORIGINAL_TITLE = "original_title";
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
    //private static final String API_KEY = "f804facb811415aff9fb6ec12310e4a6";
    private static final String BASE_URL = "http://api.themoviedb" +
            ".org/3/movie/";
    private static final String VIDEOS = "&append_to_response=videos";
    private static final String REVIEWS = "&append_to_response=reviews";
    public static final String MOVIE_ID = "id";

    private ArrayList<MovieItem> parseItems(JSONObject jsonBody)
            throws IOException, JSONException {


        JSONArray moviesJsonArray = jsonBody.getJSONArray(TMDB_RESULTS);
        Log.i("Number of Movies = ", "" + moviesJsonArray.length());

        ArrayList<MovieItem> movies = new ArrayList<>();

        for (int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject movieObject = moviesJsonArray.getJSONObject(i);

            MovieItem mMovie = new MovieItem();

            // Get the title of the movie poster
            mMovie.setTitle(movieObject.getString(TMDB_ORIGINAL_TITLE));

            // Get the movie image
            String mPosterPath = movieObject.getString(TMDB_POSTER_PATH);
            Log.v(TAG, "Poster Path = " + mPosterPath);
            String mPosterUrl = POSTER_BASE_URL + mPosterPath;
            Log.v(TAG, "Poster URL = " + mPosterUrl);
            mMovie.setImage(mPosterUrl);

            // Get user rating (called vote_average in the api)
            mMovie.setVoteAverage(movieObject.getString(TMDB_VOTE_AVERAGE));

            // Get the plot synopsis (called overview in the api)
            mMovie.setOverView(movieObject.getString(TMDB_OVERVIEW));

            // Get the release date
            mMovie.setReleaseDate(movieObject.getString(TMDB_RELEASE_DATE));

            // Get the movie id
            mMovie.setMovieId(movieObject.getInt(MOVIE_ID));

//            String trailerId = BASE_URL + mMovie.getMovieId() + "?api_key=" + API_KEY + VIDEOS;
//            Log.i("Sheila ~ trailerId =>", trailerId);
//
//            // Get the trailer
//            mMovie.setTrailer(trailerId);
//
//            String reviews = BASE_URL + mMovie.getMovieId() + "?api_key=" + API_KEY + REVIEWS;
//            Log.i("Sheila ~ reviews =>", reviews);

//            // Get the reviews
//            mMovie.setReviews(reviews);

            movies.add(mMovie);
        }
        return movies;
    }
}
