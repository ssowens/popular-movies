package com.ssowens.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by Sheila Owens on 12/18/16.
 */

public class SettingsActivity extends AppCompatActivity {


    public final static String TAG = "SettingsActivity";
    public static final String PREF_MOVIES_KEY = "movies";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.popular_movies_settings);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        // Set the default values when this method has never been called.
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//
//        // Add 'general' preferences, defined in the XML file
//        addPreferencesFromResource(R.xml.preferences);
//
//        LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent()
//                .getParent()
//                .getParent();
//
//
//        // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
//        // updated when the preference changes.
//        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_movies_key)));
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
