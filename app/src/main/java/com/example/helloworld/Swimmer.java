package com.example.helloworld;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Swimmer implements Parcelable {

    public static final Creator<Swimmer> CREATOR = new Creator<Swimmer>() {
        @Override
        public Swimmer createFromParcel(Parcel in) {
            return new Swimmer(in);
        }

        @Override
        public Swimmer[] newArray(int size) {
            return new Swimmer[size];
        }
    };

    private String name;
    private ArrayList<Lap> laps;

    public Swimmer (String name, ArrayList<Lap> laps) {
        this.name = name;
        this.laps = laps;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName () {
        return name;
    }

    public void addLaps(long overallTime) { // automatically calculates the split times
        laps.add(new Lap(overallTime, laps.size()));
        if (laps.size() == 1) {
            laps.get(0).setSplitTime(overallTime);
        }
        else {
            laps.get(laps.size()-1).setSplitTime(overallTime - laps.get(laps.size()-2).getOverallTime());
        }

    }

    public ArrayList<Lap> getLaps () { // method used for SplitListRecViewAdapter makes a null first object for header
        laps.add(0, null);
        return laps;
    }

    public ArrayList<Lap> getExistingLaps() {
        return laps;
    }

    public Swimmer(Parcel in) {
        this.name = in.readString();
        this.laps = in.readArrayList(Lap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeList(laps);
    }
}
