package com.example.android.quakereport;

public class Quake {

     // decimal value
    private String mMagnitude;

    // String value
    private String mPlace;

    // Long Integer value
    private String mDate;



    /**
     * Constructs a new object containing magnitude, place and date
     */
    public Quake(String Magnitude, String Place, String Date) {
        mMagnitude = Magnitude;
        mPlace = Place;
        mDate = Date;

    }


    //get the magnitude
    public String getMagnitude() {
        return mMagnitude;
    }

    //get the location
    public String getPlace() {
        return mPlace;
    }

    //get the date the earthquake happened
    public String getDate() {
        return mDate;
    }


}
