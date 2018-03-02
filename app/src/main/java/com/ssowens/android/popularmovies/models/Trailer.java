package com.ssowens.android.popularmovies.models;

import com.google.gson.annotations.SerializedName;
import com.ssowens.android.popularmovies.MovieVideo;

import java.util.List;

/**
 * Created by Sheila Owens on 2/4/18.
 */

public class Trailer {

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

    @Override
    public String toString() {
        return "Trailer{" +
                "id=" + id +
                ", trailerItems=" + trailerItems +
                '}';
    }
}
