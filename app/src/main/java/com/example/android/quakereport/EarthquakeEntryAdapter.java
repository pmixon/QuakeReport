package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.net.URI;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by phill on 12/26/2017.
 */

public class EarthquakeEntryAdapter extends ArrayAdapter<EarthquakeEntry> {
    public EarthquakeEntryAdapter(@NonNull Context context, ArrayList<EarthquakeEntry> earthquakes) {
        super(context,0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View earthquakeView = convertView;
        if (earthquakeView == null) {
            earthquakeView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_entry,parent,false);
        }

        final EarthquakeEntry currentEarthquake = getItem(position);

        double formatDouble = currentEarthquake.getmMagnitude();
        DecimalFormat formatter = new DecimalFormat("0.00");
        String formattedMag = formatter.format(formatDouble);

        TextView magnitudeTextView = (TextView) earthquakeView.findViewById(R.id.magnitude);
        magnitudeTextView.setText(formattedMag);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
        magnitudeCircle.setColor(ContextCompat.getColor(getContext(), getMagnitudeColor(formatDouble)));

        String baseString = currentEarthquake.getmCityName();
        int splitStringIndex = baseString.indexOf(" of ");

        String splitString;
        String splitStringTwo;

        if (splitStringIndex == -1) {
            splitString = "Near the";
            splitStringTwo = baseString;
        }
        else {
            splitString = baseString.substring(0,splitStringIndex + 4);
            splitStringTwo = baseString.substring(splitStringIndex + 4, baseString.length());
        }

        TextView areaTextView = (TextView) earthquakeView.findViewById(R.id.area);
        areaTextView.setText(splitString);

        TextView cityTextView = (TextView) earthquakeView.findViewById(R.id.city);
        cityTextView.setText(splitStringTwo);

        Date newDate = new Date(currentEarthquake.getmDate());

        TextView dateTextView = (TextView) earthquakeView.findViewById(R.id.date);
        dateTextView.setText(formatDate(newDate));

        TextView timeTextView = (TextView) earthquakeView.findViewById(R.id.time);
        timeTextView.setText(formatTime(newDate));

        return earthquakeView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude) {

        int magnitudeColorId;
        DecimalFormat magFormatter = new DecimalFormat("0");
        String graphicFormatter = magFormatter.format(magnitude);

        switch (graphicFormatter) {
            case "1":
                magnitudeColorId = R.color.magnitude1;
                break;
            case "2":
                magnitudeColorId = R.color.magnitude2;
                break;
            case "3":
                magnitudeColorId = R.color.magnitude3;
                break;
            case "4":
                magnitudeColorId = R.color.magnitude4;
                break;
            case "5":
                magnitudeColorId = R.color.magnitude5;
                break;
            case "6":
                magnitudeColorId = R.color.magnitude6;
                break;
            case "7":
                magnitudeColorId = R.color.magnitude7;
                break;
            case "8":
                magnitudeColorId = R.color.magnitude8;
                break;
            case "9":
                magnitudeColorId = R.color.magnitude9;
                break;
            case "10":
                magnitudeColorId = R.color.magnitude10plus;
                break;
            default:
                magnitudeColorId = R.color.magnitude1;
        }

        return magnitudeColorId;
    }
}
