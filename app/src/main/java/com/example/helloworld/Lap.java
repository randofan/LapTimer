package com.example.helloworld;

import android.os.Parcel;
import android.os.Parcelable;

public class Lap implements Parcelable {

    private int lapNumber;
    private long overallTime;
    private long splitTime;

    public Lap(long overallTime, int lapNumber) {
        this.overallTime = overallTime;
        this.lapNumber = lapNumber;
    }

    protected Lap(Parcel in) {
        lapNumber = in.readInt();
        overallTime = in.readLong();
        splitTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(lapNumber);
        dest.writeLong(overallTime);
        dest.writeLong(splitTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Lap> CREATOR = new Creator<Lap>() {
        @Override
        public Lap createFromParcel(Parcel in) {
            return new Lap(in);
        }

        @Override
        public Lap[] newArray(int size) {
            return new Lap[size];
        }
    };

    public void setSplitTime (long splitTime) {
        this.splitTime = splitTime;
    }
    public long getOverallTime () {
        return overallTime;
    }
    public String getOverallClockTime () {
        return changeFormat(overallTime);
    }
    public String getSplitClockTime () {
        return changeFormat(splitTime);
    }
    public String changeFormat(long newCurrentCentiseconds) {
        int seconds = (int) (newCurrentCentiseconds / 100);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int centiseconds =  (int) (newCurrentCentiseconds % 100);

        return String.format("%d:%02d.%02d", minutes,seconds,centiseconds);
    }

}
