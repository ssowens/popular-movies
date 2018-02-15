package com.ssowens.android.popularmovies.Models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.ssowens.android.popularmovies.MovieItem;

import java.util.List;

/**
 * Created by Sheila Owens on 1/18/18.
 */

public class Movie {
    private static final String TAG = "Movie";

    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    public List<MovieItem> movieItems;

    public List<MovieItem> getMovieItems() {
        return movieItems;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public Movie(List<MovieItem> movieItems) {
        this.movieItems = movieItems;
    }

    @Override
    public String toString() {
        if (movieItems != null) {
            Log.i("Movie", "this works");
        }
        return "Movie{" +
                "page=" + page +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                ", movieItems=" + movieItems +
                '}';
    }
}
