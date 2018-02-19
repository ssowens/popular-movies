package com.ssowens.android.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssowens.android.popularmovies.Models.Trailer;
import com.ssowens.android.popularmovies.databinding.FragmentMovieDetailBinding;

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
    public static final String ARG_MOVIE_ID = "id";
    public static final String ARG_TRAILER_KEY = "key";

    private ImageView movieImage;
    private ImageButton playVideoBtn;
    private TextView trailerTextView;

    private String imageUrl;
    private String movieTitleStr;
    private String releaseDateStr;
    private String voteAverateStr;
    private String overviewStr;
    private String trailerStr;
    private String movieIdStr;
    private String trailerKey;
    public String key = "";
    private Gson gson;
    private RequestQueue requestQueue;
    private LinearLayout trailerLayout;
    private Trailer trailer;

    public static MovieDetailFragment newInstance(String movieUrl,
                                                  String movieTitle,
                                                  String releaseDate,
                                                  String voteAverage,
                                                  String overview,
                                                  String movieId,
                                                  String trailer) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_URL, movieUrl);
        args.putSerializable(ARG_MOVIE_TITLE, movieTitle);
        args.putSerializable(ARG_MOVIE_RELEASE_DATE, releaseDate);
        args.putSerializable(ARG_MOVIE_VOTE_AVERAGE, voteAverage);
        args.putSerializable(ARG_MOVIE_OVERVIEW, overview);
        args.putSerializable(ARG_MOVIE_TRAILER, trailer);
        args.putSerializable(ARG_MOVIE_ID, movieId);

        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageUrl = (String) getArguments().getSerializable(ARG_MOVIE_URL);
        movieTitleStr = (String) getArguments().getSerializable(ARG_MOVIE_TITLE);
        releaseDateStr = (String) getArguments().getSerializable(ARG_MOVIE_RELEASE_DATE);
        voteAverateStr = (String) getArguments().getSerializable(ARG_MOVIE_VOTE_AVERAGE);
        overviewStr = (String) getArguments().getSerializable(ARG_MOVIE_OVERVIEW);
        trailerStr = (String) getArguments().getSerializable(ARG_MOVIE_TRAILER);
        movieIdStr = (String) getArguments().getSerializable(ARG_MOVIE_ID);

        requestQueue = Volley.newRequestQueue(getActivity());
        gson = new Gson();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        fetchTrailers(trailerStr);
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

        binding.movieTitleTextView.setText(movieTitleStr);
        binding.movieOverviewTextView.setText(overviewStr);
        binding.movieVoteAverageTextView.setText(voteAverateStr);
        binding.movieReleaseDateTextView.setText(releaseDateStr);

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
                play.setOnClickListener(new TrailerOnClickListener(youTubebaseUrl + video.getKey()));

                trailerLayout.addView(listItem);
            }
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

    private final Response.Listener<String> onTrailerLoaded = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.i(TAG, "Loading trailer");
            List<Trailer> trailerObject = Arrays.asList(gson.fromJson(response, Trailer.class));
            Log.i("MovieGridFragment", trailerObject.size() + " trailers loaded.");
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

    private final Response.ErrorListener onTrailerError = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("MovieGridFragment", error.toString());
        }
    };
}
