package com.ssowens.android.popularmovies.Models;

import com.google.gson.annotations.SerializedName;
import com.ssowens.android.popularmovies.TrailerItem;

import java.util.List;

/**
 * Created by Sheila Owens on 2/4/18.
 */

public class Trailer {

    private static final String TAG = "Trailer";

    private int id;

    @SerializedName("results")
    public List<TrailerItem> trailerItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TrailerItem> getTrailerItems() {
        return trailerItems;
    }

    public void setTrailerItems(List<TrailerItem> trailerItems) {
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
