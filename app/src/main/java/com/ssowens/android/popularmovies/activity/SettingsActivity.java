package com.ssowens.android.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.ssowens.android.popularmovies.R;

/**
 * Created by Sheila Owens on 12/18/16.
 */

public class SettingsActivity extends AppCompatActivity {


    public final static String TAG = "SettingsActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.popular_movies_settings);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
