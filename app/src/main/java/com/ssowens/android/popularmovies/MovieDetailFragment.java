package com.ssowens.android.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.squareup.picasso.Picasso;

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
    private static final String VIDEO_ID = "cdgQpa1pUUE";

    private ImageView mImage;
    private TextView mTitle;
    private TextView mOverview;
    private TextView mVoteAverage;
    private TextView releasteDate;
    private Button playVideoBtn;
    private RecyclerView trailerRecyclerView;

    private String mImageUrl;
    private String mMovieTitleStr;
    private String mReleaseDateStr;
    private String mVoteAverateStr;
    private String mOverviewStr;
    private String trailerStr;
    private String movieIdStr;
    private int startIndexEditText = 0;


    public ProgressBar progressBar;


    public static MovieDetailFragment newInstance(String movieUrl,
                                                  String movieTitle,
                                                  String releaseDate,
                                                  String voteAverage,
                                                  String overview,
                                                  String trailer,
                                                  String movieId) {
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

        mImageUrl = (String) getArguments().getSerializable(ARG_MOVIE_URL);
        mMovieTitleStr = (String) getArguments().getSerializable(ARG_MOVIE_TITLE);
        mReleaseDateStr = (String) getArguments().getSerializable(ARG_MOVIE_RELEASE_DATE);
        mVoteAverateStr = (String) getArguments().getSerializable(ARG_MOVIE_VOTE_AVERAGE);
        mOverviewStr = (String) getArguments().getSerializable(ARG_MOVIE_OVERVIEW);
        trailerStr = (String) getArguments().getSerializable(ARG_MOVIE_TRAILER);
        movieIdStr = (String) getArguments().getSerializable(ARG_MOVIE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Activity activity = getActivity();
        Context context = getContext();

        mImage = view.findViewById(R.id.image);
        mTitle = view.findViewById(R.id.movie_title_text_view);
        mOverview = view.findViewById(R.id.movie_overview_text_view);
        mVoteAverage = view.findViewById(R.id.movie_vote_average_text_view);
        releasteDate = view.findViewById(R.id.movie_release_date_text_view);
        playVideoBtn = view.findViewById(R.id.start_video_button);
        trailerRecyclerView = view.findViewById(R.id.trailer_recycler_view);
        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create a progress bar
        // progressBar = new ProgressBar(this);

        Picasso.with(getContext()).load(mImageUrl).into(mImage);
        mTitle.setText(mMovieTitleStr);
        mOverview.setText(mOverviewStr);
        mVoteAverage.setText(mVoteAverateStr);
        releasteDate.setText(mReleaseDateStr);
        playVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int startIndex = parseInt("0", 0);
                int startTimeMillis = parseInt("0", 0) * 1000;
                boolean autoplay = false;
                boolean lightboxMode = false;

                // Start YouTube Video
                Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(getActivity(),
                        VIDEO_ID, true, false);
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

    private class TrailerListFragment extends RecyclerView.ViewHolder {

        public TrailerListFragment(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_trailer, parent, false));
        }
    }

    private class TrailerAdapter extends RecyclerView.Adapter<TrailerHolder> {

        @Override
        public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            return null;
        }

        @Override
        public void onBindViewHolder(TrailerHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private class TrailerHolder extends RecyclerView.ViewHolder {

        public TrailerHolder(View itemView) {
            super(itemView);
        }
    }
}
