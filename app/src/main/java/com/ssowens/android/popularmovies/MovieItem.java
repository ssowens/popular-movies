package com.ssowens.android.popularmovies;

import java.util.UUID;

/**
 * Created by Sheila Owens on 12/11/16.
 */

class MovieItem {

    private UUID mId;
    private int moviePosition;
    private String image;
    private String title;
    private String overView;
    private String voteAverage;

    MovieItem() {
        mId = UUID.randomUUID();
    }

    public int getMoviePosition() {
        return moviePosition;
    }

    public void setMoviePosition(int moviePosition) {
        this.moviePosition = moviePosition;
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

    private String releaseDate;


}
