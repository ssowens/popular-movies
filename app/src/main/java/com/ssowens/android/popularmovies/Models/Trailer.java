package com.ssowens.android.popularmovies.Models;

import com.google.gson.annotations.SerializedName;
import com.ssowens.android.popularmovies.MovieVideo;

import java.util.List;

/**
 * Created by Sheila Owens on 2/4/18.
 */

public class Trailer {

    private static final String TAG = "Trailer";

    private int id;

    @SerializedName("results")
    public List<MovieVideo> trailerItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieVideo> getTrailerItems() {
        return trailerItems;
    }

    public void setTrailerItems(List<MovieVideo> trailerItems) {
        this.trailerItems = trailerItems;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "id=" + id +
                ", trailerItems=" + trailerItems +
                '}';
    }
}
