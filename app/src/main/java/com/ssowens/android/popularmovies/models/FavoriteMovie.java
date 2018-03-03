package com.ssowens.android.popularmovies.models;

/**
 * Created by Sheila Owens on 2/25/18.
 */

public class FavoriteMovie {

    private long movieId;
    private String poster_path;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Integer getMovieid() {
        return movieid;
    }

    public void setMovieid(Integer movieid) {
        this.movieid = movieid;
    }

    private Integer movieid;

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


}
