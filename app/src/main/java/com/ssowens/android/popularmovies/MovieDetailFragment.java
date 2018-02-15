package com.ssowens.android.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.squareup.picasso.Picasso;
import com.ssowens.android.popularmovies.Models.Trailer;

import java.util.List;

public class MovieDetailFragment extends Fragment {

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
    private TextView title;
    private TextView overview;
    private TextView voteAverage;
    private TextView releasteDate;
    private ImageButton playVideoBtn;
    private TextView trailerTextView;

    private String mImageUrl;
    private String mMovieTitleStr;
    private String mReleaseDateStr;
    private String mVoteAverateStr;
    private String mOverviewStr;
    private String trailerStr;
    private String movieIdStr;
    private String trailerKey;

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

        mImageUrl = (String) getArguments().getSerializable(ARG_MOVIE_URL);
        mMovieTitleStr = (String) getArguments().getSerializable(ARG_MOVIE_TITLE);
        mReleaseDateStr = (String) getArguments().getSerializable(ARG_MOVIE_RELEASE_DATE);
        mVoteAverateStr = (String) getArguments().getSerializable(ARG_MOVIE_VOTE_AVERAGE);
        mOverviewStr = (String) getArguments().getSerializable(ARG_MOVIE_OVERVIEW);
        trailerStr = (String) getArguments().getSerializable(ARG_MOVIE_TRAILER);
        movieIdStr = (String) getArguments().getSerializable(ARG_MOVIE_ID);
        trailerKey = (String) getArguments().getSerializable(ARG_TRAILER_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Activity activity = getActivity();
        Context context = getContext();

        movieImage = view.findViewById(R.id.image);
        title = view.findViewById(R.id.movie_title_text_view);
        overview = view.findViewById(R.id.movie_overview_text_view);
        voteAverage = view.findViewById(R.id.movie_vote_average_text_view);
        releasteDate = view.findViewById(R.id.movie_release_date_text_view);
        playVideoBtn = view.findViewById(R.id.start_video_button);
        LinearLayout trailerLayout = view.findViewById(R.id.trailer_area);
        trailerTextView = view.findViewById(R.id.trailer_text_view);

        Picasso.with(getContext()).load(mImageUrl).into(movieImage);
        title.setText(mMovieTitleStr);
        overview.setText(mOverviewStr);
        voteAverage.setText(mVoteAverateStr);
        releasteDate.setText(mReleaseDateStr);
        if (trailerStr != null) {
            trailerTextView.setText("Trailer 1");
        }
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

    private void updateUI() {
        Trailer trailers = new Trailer();
        List<TrailerItem> trailerItem = trailers.getTrailerItems();
    }

}
