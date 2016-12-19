package com.ssowens.android.popularmovies;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    public final static String LOG_TAG = "MainActivity";

    @Override
    protected Fragment createFragment() {
        return MovieGridFragment.newInstance();
    }

}

