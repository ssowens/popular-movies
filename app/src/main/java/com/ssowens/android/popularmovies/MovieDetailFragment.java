package com.ssowens.android.popularmovies;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssowens.android.popularmovies.data.FavoriteMovieLoader;
import com.ssowens.android.popularmovies.databinding.FragmentMovieDetailBinding;
import com.ssowens.android.popularmovies.models.MovieReview;
import com.ssowens.android.popularmovies.models.Review;
import com.ssowens.android.popularmovies.models.Trailer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieDetailFragment extends Fragment {

    private static final String TAG = MovieDetailFragment.class.getSimpleName();

    public static final String ARG_MOVIE_URL = "movie_url";
    public static final String ARG_MOVIE_TITLE = "movie_title";
    public static final String ARG_MOVIE_RELEASE_DATE = "movie_release_date";
    public static final String ARG_MOVIE_VOTE_AVERAGE = "movie_vote_average";
    public static final String ARG_MOVIE_OVERVIEW = "movie_overview";
    public static final String ARG_MOVIE_TRAILER = "movie_trailer";
    public static final String ARG_MOVIE_REVIEW = "movie_review";
    public static final String ARG_MOVIE_ID = "id";

    private ImageView movieImage;
    private ImageButton playVideoBtn;
    private TextView trailerTextView;

    private int movieId;
    private String imageUrl;
    private String movieTitleStr;
    private String releaseDateStr;
    private String voteAverateStr;
    private String overviewStr;
    private Gson gson;
    private RequestQueue requestQueue;
    private LinearLayout trailerLayout;
    private LinearLayout reviewLayout;
    private Trailer trailer;
    SQLiteDatabase db;

    public static MovieDetailFragment newInstance(String movieUrl,
                                                  String movieTitle,
                                                  String releaseDate,
                                                  String voteAverage,
                                                  String overview,
                                                  String movieId,
                                                  String trailer,
                                                  String reviewUrl) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_URL, movieUrl);
        args.putSerializable(ARG_MOVIE_TITLE, movieTitle);
        args.putSerializable(ARG_MOVIE_RELEASE_DATE, releaseDate);
        args.putSerializable(ARG_MOVIE_VOTE_AVERAGE, voteAverage);
        args.putSerializable(ARG_MOVIE_OVERVIEW, overview);
        args.putSerializable(ARG_MOVIE_TRAILER, trailer);
        args.putSerializable(ARG_MOVIE_ID, movieId);
        args.putSerializable(ARG_MOVIE_REVIEW, reviewUrl);

        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String movieStr = (String) getArguments().getSerializable(ARG_MOVIE_ID);
        Log.i(TAG, "Sheila This is the movieID => " + movieStr);
        try {
            //TODO THIS MIGHT CAUSE A PROBLEM
            // movieId = Long.parseLong(String.valueOf(movieStr));
            movieId = Integer.parseInt(String.valueOf(movieStr));
            Log.i(TAG, "Sheila convert movieId to integer");
        } catch (Exception ex) {
            Log.e(TAG, "Sheila ERROR:Converting Movie Id from a string to integer ~ failed");
        }

        imageUrl = (String) getArguments().getSerializable(ARG_MOVIE_URL);
        movieTitleStr = (String) getArguments().getSerializable(ARG_MOVIE_TITLE);
        releaseDateStr = (String) getArguments().getSerializable(ARG_MOVIE_RELEASE_DATE);
        voteAverateStr = (String) getArguments().getSerializable(ARG_MOVIE_VOTE_AVERAGE);
        overviewStr = (String) getArguments().getSerializable(ARG_MOVIE_OVERVIEW);
        String trailerStr = (String) getArguments().getSerializable(ARG_MOVIE_TRAILER);
        String reviewStr = (String) getArguments().getSerializable(ARG_MOVIE_REVIEW);

        requestQueue = Volley.newRequestQueue(getActivity());
        gson = new Gson();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        fetchTrailers(trailerStr);
        fetchReviews(reviewStr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Setup DataBinding
        FragmentMovieDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_movie_detail, container, false);
        View view = binding.getRoot();
        binding.setViewModel(new MovieItem(imageUrl));

        trailerLayout = view.findViewById(R.id.trailer_list);
        reviewLayout = view.findViewById(R.id.review_list);

        binding.movieTitleTextView.setText(movieTitleStr);
        binding.movieOverviewTextView.setText(overviewStr);
        binding.movieVoteAverageTextView.setText(voteAverateStr);
        binding.movieReleaseDateTextView.setText(releaseDateStr);

        MaterialFavoriteButton favoriteButton = new MaterialFavoriteButton.Builder(getActivity())
                .favorite(true)
                .color(MaterialFavoriteButton.STYLE_BLACK)
                .type(MaterialFavoriteButton.STYLE_HEART)
                .rotationDuration(400)
                .create();

        binding.movieFavorite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean
                    favorite) {
                MovieItem favoriteMovie = new MovieItem();
                favoriteMovie.setMovieId((long) movieId);
                favoriteMovie.setImage(imageUrl);
                favoriteMovie.setTitle(movieTitleStr);
                favoriteMovie.setOverView(overviewStr);
                favoriteMovie.setVoteAverage(voteAverateStr);
                favoriteMovie.setReleaseDate(releaseDateStr);

                FavoriteMovieLoader fav = new FavoriteMovieLoader(getActivity(), getView());
                fav.addFavoriteMovies(favoriteMovie);
            }
        });

        return view;
    }

    public void appendTrailers(List<MovieVideo> trailers) {
        Log.v(TAG, "appendTrailers");
        String youTubebaseUrl = getContext().getString(R.string.youtube_base_url);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (MovieVideo video : trailers) {
            if (video.getType().equals("Trailer")) {
                View listItem = inflater.inflate(R.layout.list_item_trailer, trailerLayout,
                        false);

                TextView title = listItem.findViewById(R.id.trailer_title);
                ImageView play = listItem.findViewById(R.id.play_button);

                title.setText(video.getName());
                play.setOnClickListener(new TrailerOnClickListener(youTubebaseUrl +
                        video.getKey()));

                trailerLayout.addView(listItem);
            }
        }
    }

    public void appendReviews(List<MovieReview> reviews) {
        Log.v(TAG, "appendReviews");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (MovieReview review : reviews) {
            View listItem = inflater.inflate(R.layout.list_item_review, reviewLayout, false);

            TextView content = listItem.findViewById(R.id.review_content);
            TextView author = listItem.findViewById(R.id.review_author);
            TextView fullReviewUrl = listItem.findViewById(R.id.reviewUrl);

            fullReviewUrl.setClickable(true);
            fullReviewUrl.setMovementMethod(LinkMovementMethod.getInstance());
            String link = "<a href='" + review.getUrl() + "'> Full " +
                    "Review </a>";
            Log.i(TAG, "link" + link);

            content.setText(review.getContent());
            author.setText(review.getAuthor());
            fullReviewUrl.setText(Html.fromHtml(link));

            reviewLayout.addView(listItem);
        }
    }

    public class TrailerOnClickListener implements View.OnClickListener {

        String trailerUrl;

        public TrailerOnClickListener(String trailerUrl) {
            this.trailerUrl = trailerUrl;
        }

        @Override
        public void onClick(View v) {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl)));
        }
    }

    private void fetchTrailers(String endPoint) {
        Log.i(TAG, "fetchTrailers()");
        StringRequest request = new StringRequest(Request.Method.GET, endPoint, onTrailerLoaded,
                onTrailerError);
        requestQueue.add(request);
    }

    private void fetchReviews(String endPoint) {
        Log.i(TAG, "fetchReviews");
        StringRequest request = new StringRequest(Request.Method.GET, endPoint, onReviewLoaded,
                onReviewError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onTrailerLoaded = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.i(TAG, "Loading trailers");
            List<Trailer> trailerObject = Arrays.asList(gson.fromJson(response, Trailer.class));
            ArrayList<MovieVideo> trailerItems = new ArrayList<>();

            for (Trailer trailer : trailerObject) {
                for (int iter = 0; iter < trailer.getTrailerItems().size(); iter++) {
                    MovieVideo eachTrailer = new MovieVideo();
                    if (trailer.getTrailerItems().get(iter).getType().equals("Trailer")) {
                        eachTrailer.setIso_639_1(trailer.getTrailerItems().get(iter).getIso_639_1());
                        eachTrailer.setIso_3166_1(trailer.getTrailerItems().get(iter).getIso_3166_1());
                        eachTrailer.setKey(trailer.getTrailerItems().get(iter).getKey());
                        eachTrailer.setName(trailer.getTrailerItems().get(iter).getName());
                        eachTrailer.setSite(trailer.getTrailerItems().get(iter).getSite());
                        eachTrailer.setSize(trailer.getTrailerItems().get(iter).getSize());
                        eachTrailer.setType(trailer.getTrailerItems().get(iter).getType());
                        trailerItems.add(eachTrailer);
                    }
                }
            }
            appendTrailers(trailerItems);
        }
    };

    private final Response.Listener<String> onReviewLoaded = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.i(TAG, "Loading reviews");
            List<Review> reviewObject = Arrays.asList(gson.fromJson(response, Review.class));
            ArrayList<MovieReview> reviewItems = new ArrayList<>();

            for (Review review : reviewObject) {
                for (int iter = 0; iter < review.getReviewItems().size(); iter++) {
                    MovieReview eachReview = new MovieReview();
                    eachReview.setReviewId(review.getReviewItems().get(iter).getReviewId());
                    eachReview.setAuthor(review.getReviewItems().get(iter).getAuthor());
                    eachReview.setContent(review.getReviewItems().get(iter).getContent());
                    eachReview.setUrl(review.getReviewItems().get(iter).getUrl());
                    reviewItems.add(eachReview);
                }
            }
            appendReviews(reviewItems);
        }
    };

    private final Response.ErrorListener onTrailerError = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("MovieGridFragment", error.toString());
        }
    };

    private final Response.ErrorListener onReviewError = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("MovieGridFragment", error.toString());
        }
    };
}
