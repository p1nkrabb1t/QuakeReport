/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import android.app.LoaderManager.LoaderCallbacks;


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Quake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /**
     * URL to query the USGS dataset for earthquake information
     */
    private static final String DATA_SOURCE_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?";

    /**
     * Adapter for the list of earthquakes
     */
    private EarthquakeAdapter mAdapter;
    public TextView test;
    public ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //check for network connection
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        // Find a reference to the {@link ListView} in the layout
        ListView listView = (ListView) findViewById(R.id.list);

        test = (TextView) findViewById(R.id.empty_text);
        listView.setEmptyView(test);

        // Create a fake list of earthquakes.
        ArrayList<Quake> earthquakesList = new ArrayList<>();

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeAdapter(this, earthquakesList);



        progress = (ProgressBar) findViewById(R.id.progress_bar);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        listView.setAdapter(mAdapter);


        // Set a click listener on the earthquake item
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Quake currentSelection = mAdapter.getItem(position);
                Uri url = Uri.parse(currentSelection.getUrl());

                Intent i = new Intent(Intent.ACTION_VIEW, url);

                startActivity(i);
            }
        });

        if (isConnected) {

            //new ConnectSyncTask().execute(DATA_SOURCE_URL);
            getLoaderManager().initLoader(0, null, this).forceLoad();
        }
        else {
            //Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
            progress.setVisibility(View.GONE);
            test.setText("No Internet");
        }

    }


    @Override
    public Loader<List<Quake>> onCreateLoader(int id, Bundle bundle) {
        //version 1 now unused
        //return new EarthquakeLoader(EarthquakeActivity.this, DATA_SOURCE_URL);

        //Version 2 to allow prefs here:
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy  = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(DATA_SOURCE_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `format=geojson`
        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        // Return the completed uri `http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=10&minmag=minMagnitude&orderby=time
        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Quake>> loader, List<Quake> earthquakeInfo) {
        progress.setVisibility(View.GONE);
        test.setText("No Results");
        // Clear the adapter of previous earthquake data
        //mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
       if (earthquakeInfo != null && !earthquakeInfo.isEmpty()) {
          mAdapter.addAll(earthquakeInfo);
           //updateUi(earthquakeInfo);
        }


    }

    @Override
    public void onLoaderReset(Loader<List<Quake>> loader) {
        mAdapter.clear();

    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // private class ConnectSyncTask extends AsyncTask<String, Void, List<Quake>> {


//        @Override
//        protected List<Quake> doInBackground(String... urls) {
//
//
//            // Don't perform the request if there are no URLs, or the first URL is null.
//            if (urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//            // Perform the HTTP request for earthquake data and process the response.
//            // 'USGS_REQUEST_URL' is currently at array index 0
//            List<Quake> earthquakeList = QueryUtils.fetchEarthquakeData(urls[0]);
//            return earthquakeList;
//
//        }

//        @Override
//        protected void onPostExecute(List<Quake> earthquakeInfo) {
//
////            // Clear the adapter of previous earthquake data
//            mAdapter.clear();
//
//            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//            // data set. This will trigger the ListView to update.
//            if (earthquakeInfo != null && !earthquakeInfo.isEmpty()) {
//                mAdapter.addAll(earthquakeInfo);
//            }
//
//        }
//}


}
