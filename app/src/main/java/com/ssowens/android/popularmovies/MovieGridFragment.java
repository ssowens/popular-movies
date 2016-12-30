package com.ssowens.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import java.util.ArrayList;

import static com.ssowens.android.popularmovies.MovieFetchr.POPULAR_MOVIE_URL;
import static com.ssowens.android.popularmovies.MovieFetchr.TOP_RATED_MOVIE_URL;
import static com.ssowens.android.popularmovies.R.id.gridView;

/**
 * Created by Sheila Owens on 12/11/16.
 */

public class MovieGridFragment extends Fragment {

    private static final String TAG = MovieGridFragment.class.getSimpleName();


    private GridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewAdapter mGridAdapter;
    private ArrayList<MovieItem> mGridData = new ArrayList<>();
    //public String mMoviesSelection = POPULAR_MOVIE_URL;
    public ActionBar actionBar;
    public static final String EXTRA_DATA = "com.example.android.sunshine.app.DATA";

    public static MovieGridFragment newInstance() {

        return new MovieGridFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


        // Set this option to indicate that the fragment has a menu events
        setHasOptionsMenu(true);

        // Set this option for defaults in the Settings screen
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);

        // This call starts the AsyncTask which will start a background thread
        // and kick off doInBackground().

        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        final GridView mGridView = (GridView) rootView.findViewById(gridView);

        // Get a reference to the GridView, and attach this adapter to it.
        mGridAdapter = new GridViewAdapter(getActivity(), mGridData);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),
                        "Item Clicked: " + position, Toast.LENGTH_SHORT).show();

                String movieItem = mGridAdapter.getItem(position).toString();

                //   Need a Bundel
                Intent detailIntent = new Intent(getActivity(), MovieDetailActivity.class)
                        .putExtra(EXTRA_DATA, movieItem);
                startActivity(detailIntent);


            }
        });

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        return rootView;
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<MovieItem>> {

        private final String LOG_TAG = FetchItemsTask.class.getSimpleName();

        @Override
        protected ArrayList<MovieItem> doInBackground(Void... params) {

            Log.i(TAG, "doInBackground");
            String movie_url;

            // Get the movie sort order
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String moviesSortOrder = sharedPref.getString(getString(R.string.pref_movies_key),
                    getString(R.string.pref_movies_key));

            if (moviesSortOrder.equals("0")) {
                movie_url = POPULAR_MOVIE_URL;
            } else {
                movie_url = TOP_RATED_MOVIE_URL;
            }

            mGridData = new MovieFetchr().fetchItems(movie_url);
            return mGridData;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItem> gridItems) {

            // Download complete. Let us update UI
            super.onPostExecute(gridItems);
            Log.v(TAG, "gridItems = " + gridItems.size());

            mGridAdapter.clear();
            Log.v(TAG, "gridItems = " + gridItems.size());

            if (gridItems != null) {
                mGridAdapter.addAll(gridItems);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void updateMovies() {
        FetchItemsTask moviesTask = new FetchItemsTask();
        moviesTask.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }


}
