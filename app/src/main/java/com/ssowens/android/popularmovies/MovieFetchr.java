package com.ssowens.android.popularmovies;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sheila Owens on 12/12/16.
 */

public class MovieFetchr {
    private static final String TAG = "MovieFetchr";
    private static final String TMDB_RESULTS = "results";
    private static final String TMDB_POSTER_PATH = "poster_path";
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String MOVIE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=f804facb811415aff9fb6ec12310e4a6";
    private String API_KEY = "f804facb811415aff9fb6ec12310e4a6";

    /**
     * This method fetches raw data from a URL and returns it as an array of bytes.
     *
     * @param urlSpec
     * @return
     * @throws IOException
     */
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        Log.i(TAG, "Entering getUrlBytes");
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException((connection.getResponseMessage() + ": with " + urlSpec));
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    /**
     * This method converts the results from getUrlBytes(String) to a String.
     *
     * @param urlSpec
     * @return
     * @throws IOException
     */
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }


    public ArrayList<MovieItem> fetchItems() {

        ArrayList<MovieItem> items = new ArrayList<>();
        Log.i(TAG, "Entering fetchItems");
        try {
            String url = Uri.parse(MOVIE_URL).toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            items = parseItems(jsonBody);

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return items;
    }


    private ArrayList<MovieItem> parseItems(JSONObject jsonBody)
            throws IOException, JSONException {


        JSONArray moviesJsonArray = jsonBody.getJSONArray(TMDB_RESULTS);
        Log.i("Number of Movies = ", "" + moviesJsonArray.length());

        ArrayList<MovieItem> movies = new ArrayList<>();

        for (int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject movieObject = moviesJsonArray.getJSONObject(i);

            MovieItem mMovie = new MovieItem();

            // Get the title of the movie poster
            mMovie.setTitle(movieObject.getString("original_title"));

            // Get the movie image

            String mPosterPath = movieObject.getString(TMDB_POSTER_PATH);
            Log.v(TAG, "Poster Path = " + mPosterPath);

            String mPosterUrl = POSTER_BASE_URL + mPosterPath;
            Log.v(TAG, "Poster URL = " + mPosterUrl);

            mMovie.setImage(mPosterUrl);
            movies.add(mMovie);
        }
        return movies;
    }

}