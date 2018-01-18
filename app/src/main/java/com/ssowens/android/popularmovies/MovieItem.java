package com.ssowens.android.popularmovies;

import java.util.UUID;

/**
 * Created by Sheila Owens on 12/11/16.
 * Updated by Sheila Owens on 1/14/18
 */

class MovieItem {

    private UUID mId;
    private int moviePosition;
    private String image;
    private String title;
    private String overView;
    private String voteAverage;
    private String trailer;
    private String releaseDate;
    private String reviews;
    private int movieId;

    MovieItem() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    String getImage() {
        return image;
    }

    void setImage(String image) {
        this.image = image;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    String getOverView() {
        return overView;
    }

    void setOverView(String overView) {
        this.overView = overView;
    }

    String getVoteAverage() {
        return voteAverage;
    }

    void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    String getReleaseDate() {
        return releaseDate;
    }

    void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviewsUrl) {
        this.reviews = reviewsUrl;
    }


}
