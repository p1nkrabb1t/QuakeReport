package com.example.android.quakereport;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;


public class EarthquakeAdapter extends ArrayAdapter<Quake> {

    public EarthquakeAdapter(Activity context, ArrayList<Quake> quake_info) {
        super(context, 0, quake_info);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.quake_item, parent, false);
        }
        // Get the object located at this position in the list
        Quake currentSelection = getItem(position);


        // Find and update the TextView in the quake_item.xml layout with the magnitude
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        DecimalFormat decimal = new DecimalFormat("0.0");
        double magDisplayed = currentSelection.getMagnitude();
        magnitudeTextView.setText(String.valueOf(decimal.format(magDisplayed)));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentSelection.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        //find location info and split into place and proximity if proximity info available
        String locationInfo = currentSelection.getPlace();
        String proximity = "";
        String area = "";
        String breakpoint = getContext().getString(R.string.loc_breakpoint);
        if (locationInfo.contains(breakpoint)) {
            // Split it.
            String[] parts = locationInfo.split(breakpoint);
            proximity = parts[0] + breakpoint;
            area = parts[1];


        } else {
            proximity = getContext().getString(R.string.offset);
        }

        // Find and update the TextView in the quake_item.xml layout with the nearest place
        TextView proximityTextView = (TextView) listItemView.findViewById(R.id.place_offset);
        proximityTextView.setText(proximity);

        // Find and update the TextView in the quake_item.xml layout with the nearest place
        TextView placeTextView = (TextView) listItemView.findViewById(R.id.place);
        placeTextView.setText(area);

        //Create a new date object to store the time of the earthquake in unix timestamp
        Date dateObject = new Date(currentSelection.getDate());

        // Find and update the TextView in the quake_item.xml layout with the date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("LLL DD, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        dateTextView.setText(String.valueOf(dateToDisplay));

        // Find and update the TextView in the quake_item.xml layout with the date
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a");
        String timeToDisplay = timeFormatter.format(dateObject);
        timeTextView.setText(String.valueOf(timeToDisplay));


        return listItemView;
    }
////////////////////////////////
    public int getMagnitudeColor(double magnitude){
        int colourID;
        int magFloor = (int) Math.floor(magnitude);
        switch (magFloor){
            case 1:
                colourID = R.color.magnitude1;
                break;

            case 2:
                colourID = R.color.magnitude2;
                break;

            case 3:
                colourID = R.color.magnitude3;
                break;

            case 4:
                colourID = R.color.magnitude4;
                break;

            case 5:
                colourID = R.color.magnitude5;
                break;

            case 6:
                colourID = R.color.magnitude6;
                break;

            case 7:
                colourID = R.color.magnitude7;
                break;

            case 8:
                colourID = R.color.magnitude8;
                break;

            case 9:
                colourID = R.color.magnitude9;
                break;

            default:
                colourID = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), colourID);
    }
}