package com.example.android.quakereport;

/**
 * Created by phill on 12/26/2017.
 */

public class EarthquakeEntry {

    public String getmCityName() {
        return mCityName;
    }

    public void setmCityName(String mCityName) {
        this.mCityName = mCityName;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public void setmMagnitude(double mMagnitude) {
        this.mMagnitude = mMagnitude;
    }

    public long getmDate() {
        return mDate;
    }

    public void setmDate(long mDate) {
        this.mDate = mDate;
    }

    private String mCityName;
    private double mMagnitude;
    private long mDate;

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    private String mURL;

    public EarthquakeEntry(String cityName, double magnitude, long date, String URL) {
        mCityName = cityName;
        mMagnitude = magnitude;
        mDate = date;
        mURL = URL;
    }
}
