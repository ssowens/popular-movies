package com.ssowens.android.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by Sheila Owens on 12/11/16.
 * Updated by Sheila Owens on 1/14/18
 */

public class MovieItem {

    private static final String TAG = "MovieItem";

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("id")
    private int movieId;

    boolean video;

    @SerializedName("vote_average")
    private String voteAverage;

    private String popularity;

    @SerializedName("poster_path")
    private String image;

    @SerializedName("original_title")
    private String originalTitle;

    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    private UUID mId;
    private String trailer;
    private String reviews;

    public MovieItem(String voteAverage, String originalTitle, String image, String overview) {
        this.voteAverage = voteAverage;
        this.originalTitle = originalTitle;
        this.image = image;
        this.overview = overview;
    }

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
        return originalTitle;
    }

    void setTitle(String title) {
        this.originalTitle = title;
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

    public String getOverView() {
        return overview;
    }

    public void setOverView(String overView) {
        this.overview = overView;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviewsUrl) {
        this.reviews = reviewsUrl;
    }

    @Override
    public String toString() {
        return "MovieItem{" +
                "voteCount=" + voteCount +
                ", movieId=" + movieId +
                ", video=" + video +
                ", voteAverage='" + voteAverage + '\'' +
                ", popularity='" + popularity + '\'' +
                ", image='" + image + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", mId=" + mId +
                ", overView='" + overview + '\'' +
                ", trailer='" + trailer + '\'' +
                ", reviews='" + reviews + '\'' +
                '}';
    }
}
