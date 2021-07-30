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

    public Swimmer() {
        laps = new ArrayList<>();
    }
    public Swimmer (String name) {
        this.name = name;
        laps = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName () {
        return name;
    }

    public void addLaps(long overallTime, String clockTime) {
        laps.add(new Lap(overallTime, laps.size(), clockTime));
        if (laps.size() == 1) {
            laps.get(laps.size()-1).setSplitTime(overallTime);
        }
        else {
            laps.get(laps.size()-1).setSplitTime(overallTime - laps.get(laps.size()-2).getOverallTime());
        }

    }

    protected Swimmer(Parcel in) {
        name = in.readString();
        laps = in.readArrayList(null);
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

    private class Lap {
        private int lapNumber;
        private long overallTime;
        private long splitTime;
        private String clockTime;

        public Lap(long overallTime, int lapNumber, String clockTime) {
            this.overallTime = overallTime;
            this.lapNumber = lapNumber;
            this.clockTime = clockTime;
        }
        public void setSplitTime (long splitTime) {
            this.splitTime = splitTime;
        }
        public long getOverallTime () {
            return overallTime;
        }
    }
}
