package com.ssowens.android.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sheila Owens on 2/18/18.
 */

public class Review {

    private int id;

    @SerializedName("results")
    public List<MovieReview> reviewItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieReview> getReviewItems() {
        return reviewItems;
    }

    public void setReviewItems(List<MovieReview> reviewItems) {
        this.reviewItems = reviewItems;
    }
}
