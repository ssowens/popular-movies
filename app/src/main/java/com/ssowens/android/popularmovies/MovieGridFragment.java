package com.ssowens.android.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Sheila Owens on 12/11/16.
 */

public class MovieGridFragment extends Fragment {

    private static final String TAG = MovieGridFragment.class.getSimpleName();


    private GridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewAdapter mGridAdapter;
    private ArrayList<MovieItem> mGridData = new ArrayList<>();

    public static MovieGridFragment newInstance() {

        return new MovieGridFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // This call starts the AsyncTask which will start a background thread
        // and kick off doInBackground().
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);

        //new FetchItemsTask().execute();
        // Get a reference to the GridView, and attach this adapter to it.
        mGridAdapter = new GridViewAdapter(getActivity(), mGridData);
        gridView.setAdapter(mGridAdapter);

        mGridView = (GridView) rootView.findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        mProgressBar.setVisibility(View.VISIBLE);

        return rootView;
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<MovieItem>> {

        private final String LOG_TAG = FetchItemsTask.class.getSimpleName();

        @Override
        protected ArrayList<MovieItem> doInBackground(Void... params) {

            Log.i(TAG, "doInBackground");
            mGridData = new MovieFetchr().fetchItems();
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
}
