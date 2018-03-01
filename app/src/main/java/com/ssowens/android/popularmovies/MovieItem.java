package com.ssowens.android.popularmovies;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.UUID;

/**
 * Created by Sheila Owens on 12/11/16.
 * Updated by Sheila Owens on 1/14/18
 */

public class MovieItem {

    public MovieItem(int movieId) {
        this.movieId = movieId;
    }

    private static final String TAG = "MovieItem";

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("id")
    private long movieId;

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

    public MovieItem(String voteAverage, String originalTitle, String imageUrl, String overview) {
        this.voteAverage = voteAverage;
        this.originalTitle = originalTitle;
        this.image = imageUrl;
        this.overview = overview;
    }

    public MovieItem(Long movieId) {
        this.movieId = movieId;
    }

    public MovieItem(String imageUrl) {
        this.image = imageUrl;
    }

    public MovieItem() {
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

    public String getPoster_path() {
        return image;
    }

    public void setPoster_path(String poster_path) {
        this.image = poster_path;
    }

    String getTitle() {
        return originalTitle;
    }

    void setTitle(String title) {
        this.originalTitle = title;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
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

    public String getImageUrl() {
        // The URL will come from the a model (i.e. Movie)
        return image;
    }

    public void onClickTrailer(View view) {
        Log.i(TAG, "onClickTrailer");
    }

    @BindingAdapter({"bind.imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .resize(200, 200)
                .into(view);
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
