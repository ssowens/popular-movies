package com.ssowens.android.popularmovies;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.ssowens.android.popularmovies.Models.Trailer;
import com.ssowens.android.popularmovies.databinding.FragmentMovieDetailBinding;

import java.util.List;

public class MovieDetailFragment extends Fragment {

    private static final String TAG = MovieDetailFragment.class.getSimpleName();


    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

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
    private Trailer trailer;

    private String imageUrl;
    private String movieTitleStr;
    private String releaseDateStr;
    private String voteAverateStr;
    private String overviewStr;
    private String trailerStr;
    private String movieIdStr;
    private String trailerKey;

    private LinearLayout trailerLayout;

    public static MovieDetailFragment newInstance(String movieUrl,
                                                  String movieTitle,
                                                  String releaseDate,
                                                  String voteAverage,
                                                  String overview,
                                                  String movieId,
                                                  String trailer,
                                                  String key) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_URL, movieUrl);
        args.putSerializable(ARG_MOVIE_TITLE, movieTitle);
        args.putSerializable(ARG_MOVIE_RELEASE_DATE, releaseDate);
        args.putSerializable(ARG_MOVIE_VOTE_AVERAGE, voteAverage);
        args.putSerializable(ARG_MOVIE_OVERVIEW, overview);
        args.putSerializable(ARG_MOVIE_TRAILER, trailer);
        args.putSerializable(ARG_MOVIE_ID, movieId);
        args.putSerializable(ARG_TRAILER_KEY, key);

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
        trailerKey = (String) getArguments().getSerializable(ARG_TRAILER_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Setup DataBinding
        FragmentMovieDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout
                .fragment_movie_detail, container, false);
        View view = binding.getRoot();
        binding.setViewModel(new MovieItem(imageUrl));

        trailerLayout = view.findViewById(R.id.trailer_area);

        binding.movieTitleTextView.setText(movieTitleStr);
        binding.movieOverviewTextView.setText(overviewStr);
        binding.movieVoteAverageTextView.setText(voteAverateStr);
        binding.movieReleaseDateTextView.setText(releaseDateStr);

//        ArrayList<MovieVideo> test = (ArrayList<MovieVideo>) trailer.getTrailerItems();
//        if (trailer.getTrailerItems() == null) {
//            appendTrailers(trailer.getTrailerItems());
//        }
        trailerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int startIndex = parseInt("0", 0);
                int startTimeMillis = parseInt("0", 0) * 1000;
                boolean autoplay = false;
                boolean lightboxMode = false;

                // Start YouTube Video
                Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(getActivity(),
                        trailerKey, true, false);
                getActivity().startActivity(intent);

                if (intent != null) {
                    if (canResolveIntent(intent)) {
                        startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
                    } else {
                        // Could not resolve the intent - must need to install or update the YouTube API service.
                        YouTubeInitializationResult.SERVICE_MISSING
                                .getErrorDialog(getActivity(), REQ_RESOLVE_SERVICE_MISSING).show();
                    }
                }
            }
        });
        return view;
    }

    public void appendTrailers(List<MovieVideo> trailers) {
        Log.v(TAG, "appendTrailers");
//        String baseUrl = getContext().getString(R.string.youtubeUrlBase);
        String baseUrl = "https://www.youtube.com/watch?v=";
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (MovieVideo video : trailers) {
            if (video.getType().equals("Trailer")) {
                View listItem = inflater.inflate(R.layout.list_item_trailer, trailerLayout, false);

                TextView title = listItem.findViewById(R.id.title);
                ImageView play = listItem.findViewById(R.id.play_button);

                title.setText(video.getName());
//                play.setOnClickListener(new ClickListener(baseUrl + video.getKey()));

                trailerLayout.addView(listItem);
            }
        }
    }


    private int parseInt(String text, int defaultValue) {
        if (!TextUtils.isEmpty(text)) {
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                // fall through
            }
        }
        return defaultValue;
    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getActivity().getPackageManager().queryIntentActivities
                (intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }
}
