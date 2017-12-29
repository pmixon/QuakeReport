package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phill on 12/29/2017.
 */


public class EarthquakeEntryLoader extends AsyncTaskLoader<List<EarthquakeEntry>> {

    private String mDataUrl;

    public EarthquakeEntryLoader(Context context) {
        super(context);
    }

    public EarthquakeEntryLoader(Context context, String url) {
        super(context);
        mDataUrl = url;
    }



    @Override
    public List<EarthquakeEntry> loadInBackground() {
        return QueryUtils.fetchEarthquakeData(mDataUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
