package com.example.android.quakereport;


        import android.app.Activity;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import java.util.ArrayList;


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
        magnitudeTextView.setText(String.valueOf(currentSelection.getMagnitude()));

        // Find and update the TextView in the quake_item.xml layout with the nearest place
        TextView placeTextView = (TextView) listItemView.findViewById(R.id.place);
        placeTextView.setText(currentSelection.getPlace());

        // Find and update the TextView in the quake_item.xml layout with the date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(String.valueOf(currentSelection.getDate()));


        return listItemView;
    }
}