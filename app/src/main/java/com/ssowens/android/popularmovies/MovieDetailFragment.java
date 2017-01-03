package com.ssowens.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment {

    public static final String ARG_MOVIE_URL = "movie_url";
    public static final String ARG_MOVIE_TITLE = "movie_title";
    public static final String ARG_MOVIE_RELEASE_DATE = "movie_release_date";
    public static final String ARG_MOVIE_VOTE_AVERAGE = "movie_vote_average";
    public static final String ARG_MOVIE_OVERVIEW = "movie_overview";

    private ImageView mImage;
    private TextView mTitle;
    private TextView mOverview;
    private TextView mVoteAverage;
    private TextView mReleasteDate;

    private String mImageUrl;
    private String mMovieTitleStr;
    private String mReleaseDateStr;
    private String mVoteAverateStr;
    private String mOverviewStr;

    public static MovieDetailFragment newInstance(String movieUrl,
                                                  String movieTitle,
                                                  String releaseDate,
                                                  String voteAverage,
                                                  String overview) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_URL, movieUrl);
        args.putSerializable(ARG_MOVIE_TITLE, movieTitle);
        args.putSerializable(ARG_MOVIE_RELEASE_DATE, releaseDate);
        args.putSerializable(ARG_MOVIE_VOTE_AVERAGE, voteAverage);
        args.putSerializable(ARG_MOVIE_OVERVIEW, overview);

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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        mImage = (ImageView) view.findViewById(R.id.image);
        mTitle = (TextView) view.findViewById(R.id.movie_title_text_view);
        mOverview = (TextView) view.findViewById(R.id.movie_overview_text_view);
        mVoteAverage = (TextView) view.findViewById(R.id.movie_vote_average_text_view);
        mReleasteDate = (TextView) view.findViewById(R.id.movie_release_date_text_view);

        Picasso.with(getContext()).load(mImageUrl).into(mImage);
        mTitle.setText(mMovieTitleStr);
        mOverview.setText(mOverviewStr);
        mVoteAverage.setText(mVoteAverateStr);
        mReleasteDate.setText(mReleaseDateStr);
        return view;
    }
}
