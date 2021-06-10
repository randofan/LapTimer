package com.example.helloworld;


import java.util.ArrayList;

public class Swimmer {
    private String name;
    private ArrayList<Lap> laps;

    public Swimmer() {
        laps = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName () {
        return name;
    }

    public void addLaps(long overallTime) {
        laps.add(new Lap(overallTime, laps.size()));
        if (laps.size() == 1) {
            laps.get(laps.size()-1).setSplitTime(overallTime);
        }
        else {
            laps.get(laps.size()-1).setSplitTime(overallTime - laps.get(laps.size()-2).getOverallTime());
        }

    }

    private class Lap {
        private int lapNumber;
        private long overallTime;
        private long splitTime;

        public Lap(long overallTime, int lapNumber) {
            this.overallTime = overallTime;
            this.lapNumber = lapNumber;
        }
        public void setSplitTime (long splitTime) {
            this.splitTime = splitTime;
        }
        public long getOverallTime () {
            return overallTime;
        }
    }
}
