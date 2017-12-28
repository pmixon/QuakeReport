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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String DATA_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private EarthquakeEntryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        mAdapter = new EarthquakeEntryAdapter(EarthquakeActivity.this, new ArrayList<EarthquakeEntry>());

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.earthquake_list);

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
        new GetEarthquakeDataTask().execute(DATA_URL);
    }

    private class GetEarthquakeDataTask extends AsyncTask<String, Void, List<EarthquakeEntry>> {

        @Override
        protected List<EarthquakeEntry> doInBackground(String... strings) {
            return QueryUtils.fetchEarthquakeData(DATA_URL);
        }

        @Override
        protected void onPostExecute(List<EarthquakeEntry> earthquakeEntries) {
            mAdapter.clear();
            if (earthquakeEntries != null || !earthquakeEntries.isEmpty()) {
                mAdapter.addAll(earthquakeEntries);
            }
            super.onPostExecute(earthquakeEntries);
        }
    }
}
