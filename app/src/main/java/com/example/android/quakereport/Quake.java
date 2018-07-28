package com.example.android.quakereport;

public class Quake {

    // decimal value
    private double mMagnitude;

    // String value
    private String mPlace;

    // Long Integer value
    private long mDate;

    // String value
    private String mUrl;

    /**
     * Constructs a new object containing magnitude, place and date
     */
    public Quake(double Magnitude, String Place, long Date, String Url) {
        mMagnitude = Magnitude;
        mPlace = Place;
        mDate = Date;
        mUrl = Url;

    }


    //get the magnitude
    public double getMagnitude() {
        return mMagnitude;
    }

    //get the location
    public String getPlace() {
        return mPlace;
    }

    //get the date the earthquake happened
    public long getDate() {
        return mDate;
    }

    //get the web address
    public String getUrl() {
        return mUrl;
    }

}
