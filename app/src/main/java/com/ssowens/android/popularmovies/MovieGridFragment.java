package com.ssowens.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ssowens.android.popularmovies.R.id.gridView;

/**
 * Created by Sheila Owens on 12/11/16.
 */

public class MovieGridFragment extends Fragment {

    private static final String TAG = MovieGridFragment.class.getSimpleName();

    private static final String POPULAR_MOVIES_KEY = "0";
    private static final String TOP_RATED_MOVIES_KEY = "1";
    private static final String FAVORITE_MOVIES_KEY = "2";
    private static final String API_KEY = "f804facb811415aff9fb6ec12310e4a6";
    private static final String BASE_URL = "http://api.themoviedb" +
            ".org/3/movie/";
    private static final String VIDEOS = "&append_to_response=videos";
    private static final String REVIEWS = "&append_to_response=reviews";
    public static final String POPULAR_MOVIE_URL = "http://api.themoviedb" +
            ".org/3/movie/popular?api_key=" + API_KEY;
    public static final String TOP_RATED_MOVIE_URL = "http://api.themoviedb" +
            ".org/3/movie/top_rated?api_key=" + API_KEY;

    public static final String FAVORITES_URL = "http://api.themoviedb" +
            ".org/3/movie/top_rated?api_key=" + API_KEY;
    public static final String TRAILER_URL = "http://api.themoviedb" +
            ".org/3/movie/?api_key=" + API_KEY + "?id=" + VIDEOS;
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";


    private ProgressBar progressBar;
    private GridViewAdapter gridAdapter;
    public ArrayList<MovieItem> gridData = new ArrayList<>();
    public ActionBar actionBar;
    private String title;

    private RequestQueue requestQueue;
    private Gson gson;

    private static final String ENDPOINT = "http://api.themoviedb" +
            ".org/3/movie/popular?api_key=" + API_KEY;
    private String endPoint;

    public static MovieGridFragment newInstance() {

        return new MovieGridFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();

        // Set this option to indicate that the fragment has a menu events
        setHasOptionsMenu(true);

        // Set this option for defaults in the Settings screen
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);

        requestQueue = Volley.newRequestQueue(getActivity());

        gson = new Gson();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        endPoint = ENDPOINT;
        fetchMovies(endPoint);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        final GridView mGridView = (GridView) rootView.findViewById(gridView);

        // Get a reference to the GridView, and attach this adapter to it.
        gridAdapter = new GridViewAdapter(getActivity(), gridData);
        mGridView.setAdapter(gridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the object at the clicked position. Will use later
                MovieItem item = (MovieItem) parent.getItemAtPosition(position);

                String imageUrl = item.getImage();
                String movieTitle = item.getTitle();
                String releaseDate = item.getReleaseDate();
                String voteAverage = item.getVoteAverage();
                String overview = item.getOverView();
                String trailer = item.getTrailer();
                int movieId = item.getMovieId();
                Intent intent = MovieDetailActivity.newIntent(getActivity(),
                        imageUrl,
                        movieTitle,
                        releaseDate,
                        voteAverage,
                        overview,
                        trailer,
                        String.valueOf(movieId));
                startActivity(intent);
            }
        });

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        return rootView;
    }

    private void fetchMovies(String endPoint) {
        Log.i(TAG, "fetchMovies()");
        StringRequest request = new StringRequest(Request.Method.GET, endPoint, onMoviesLoaded,
                onMoviesError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onMoviesLoaded = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.i("MovieGridFragment=>", response);

            List<Movies> movieObject = Arrays.asList(gson.fromJson(response,
                    Movies.class));

            Log.i("MovieGridFragment", movieObject.size() + " movies loaded.");

            ArrayList<MovieItem> items = new ArrayList<MovieItem>();

            for (Movies movie : movieObject) {
                Log.i("MovieGridFragment", movie.getMovieItems().get(0).getTitle());
                for (int iter = 0; iter < movie.getMovieItems().size(); iter++) {
                    MovieItem eachMovie = new MovieItem();
                    eachMovie.setMovieId(movie.getMovieItems().get(iter).getMovieId());
                    eachMovie.setTitle(movie.getMovieItems().get(iter).getTitle());
                    eachMovie.setOverView(movie.getMovieItems().get(iter).getOverView());
                    eachMovie.setReleaseDate(movie.getMovieItems().get(iter).getReleaseDate());
                    eachMovie.setVoteAverage(movie.getMovieItems().get(iter).getVoteAverage());

                    // Get the movie image
                    String posterPath = movie.getMovieItems().get(iter).getImage();
                    String posterUrl = POSTER_BASE_URL + posterPath;
                    Log.v(TAG, "Poster URL = " + posterUrl);
                    eachMovie.setImage(posterUrl);
                    items.add(eachMovie);
                }
            }
            updateUI(items);
        }
    };

    private final Response.ErrorListener onMoviesError = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("MovieGridFragment", error.toString());
        }
    };

//    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<MovieItem>> {
//
//        private final String LOG_TAG = FetchItemsTask.class.getSimpleName();
//
//        @Override
//        protected ArrayList<MovieItem> doInBackground(Void... params) {
//
//            Log.i(TAG, "doInBackground");
//
//            String movie_url;
//
//            // Get the movie sort order
//            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            String moviesSortOrder = sharedPref.getString(getString(R.string.pref_movies_key),
//                    getString(R.string.pref_movies_key));
//
//            switch (moviesSortOrder) {
//                case POPULAR_MOVIES_KEY:
//                    movie_url = POPULAR_MOVIE_URL;
//                    title = getString(R.string.pref_sort_most_popular);
//                    break;
//                case TOP_RATED_MOVIES_KEY:
//                    movie_url = TOP_RATED_MOVIE_URL;
//                    title = getString(R.string.pref_sort_top_rate);
//                    break;
//                case FAVORITE_MOVIES_KEY:
//                    movie_url = FAVORITES_URL;
//                    title = getString(R.string.pref_sort_favorites);
//                    break;
//                default:
//                    movie_url = TOP_RATED_MOVIE_URL;
//                    title = getString(R.string.pref_sort_top_rate);
//                    break;
//            }
//
//            //   gridData = new MovieFetchr().fetchItems(movie_url);
//            return gridData;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<MovieItem> gridItems) {
//
//            // Download complete. Let us update UI
//            super.onPostExecute(gridItems);
//            Log.v(TAG, "gridItems = " + gridItems.size());
//
//            gridAdapter.clear();
//            Log.v(TAG, "gridItems = " + gridItems.size());
//
//            if (gridItems != null) {
//                gridAdapter.addAll(gridItems);
//                gridAdapter.setGridData(gridItems);
//            } else {
//                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
//            }
//            progressBar.setVisibility(View.GONE);
//        }
//    }

    private void updateMovies() {
        getMovieSortOrder();
        actionBar.setTitle(title);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public void updateUI(ArrayList<MovieItem> gridItems) {
        Log.v(TAG, "gridItems = " + gridItems.size());

        gridAdapter.clear();

        if (gridItems != null) {
            gridAdapter.addAll(gridItems);
            gridAdapter.setGridData(gridItems);
        } else {
            Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.GONE);
    }

    public void getMovieSortOrder() {
        Log.v(TAG, "getMovieSortOrder");

        String movie_url;

        // Get the movie sort order
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String moviesSortOrder = sharedPref.getString(getString(R.string.pref_movies_key),
                getString(R.string.pref_movies_key));

        switch (moviesSortOrder) {
            case POPULAR_MOVIES_KEY:
                movie_url = POPULAR_MOVIE_URL;
                title = getString(R.string.pref_sort_most_popular);
                break;
            case TOP_RATED_MOVIES_KEY:
                movie_url = TOP_RATED_MOVIE_URL;
                title = getString(R.string.pref_sort_top_rate);
                break;
            case FAVORITE_MOVIES_KEY:
                movie_url = FAVORITES_URL;
                title = getString(R.string.pref_sort_favorites);
                break;
            default:
                movie_url = TOP_RATED_MOVIE_URL;
                title = getString(R.string.pref_sort_top_rate);
                break;
        }
        fetchMovies(movie_url);
    }
}
