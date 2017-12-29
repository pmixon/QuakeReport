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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthquakeEntry>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String DATA_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private EarthquakeEntryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ConnectivityManager cm =
                (ConnectivityManager)EarthquakeActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            getLoaderManager().initLoader(0, null, this);
        }
        else {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_data_bar);
            progressBar.setVisibility(View.GONE);

            TextView textView = (TextView) findViewById(R.id.no_earthquake_data_label);
            textView.setText(R.string.no_internet_message);
        }

        mAdapter = new EarthquakeEntryAdapter(EarthquakeActivity.this, new ArrayList<EarthquakeEntry>());

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.earthquake_list);
        earthquakeListView.setEmptyView(findViewById(R.id.no_earthquake_data_label));

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EarthquakeEntry currentEarthquake = mAdapter.getItem(i);
                Intent earthquakeURL = new Intent(Intent.ACTION_VIEW);
                earthquakeURL.setData(Uri.parse(currentEarthquake.getmURL()));
                startActivity(earthquakeURL);
            }
        });

        // execute task here
//        new GetEarthquakeDataTask().execute(DATA_URL);
    }

    @Override
    public Loader<List<EarthquakeEntry>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeEntryLoader(EarthquakeActivity.this, DATA_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<EarthquakeEntry>> loader, List<EarthquakeEntry> earthquakeEntries) {
        mAdapter.clear();
        if (earthquakeEntries != null || !earthquakeEntries.isEmpty()) {
            mAdapter.addAll(earthquakeEntries);
        }

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_data_bar);
        progressBar.setVisibility(View.GONE);

        TextView noDataTextView = (TextView) findViewById(R.id.no_earthquake_data_label);
        noDataTextView.setText(R.string.no_data_message);
    }

    @Override
    public void onLoaderReset(Loader<List<EarthquakeEntry>> loader) {
        List<EarthquakeEntry> newList = new ArrayList<EarthquakeEntry>();
        mAdapter.addAll(newList);
    }


    private class GetEarthquakeDataTask extends AsyncTask<String, Void, List<EarthquakeEntry>> {

        @Override
        protected List<EarthquakeEntry> doInBackground(String... strings) {
            return QueryUtils.fetchEarthquakeData(DATA_URL);
        }

        @Override
        protected void onPostExecute(List<EarthquakeEntry> earthquakeEntries) {

        }
    }
}
