package com.ssowens.android.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sheila Owens on 2/18/18.
 */

public class MovieReview {

    @SerializedName("id")
    private String reviewId;

    private String author;
    private String content;
    private String url;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
