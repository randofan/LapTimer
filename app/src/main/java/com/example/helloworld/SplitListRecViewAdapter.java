package com.example.helloworld;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SplitListRecViewAdapter extends RecyclerView.Adapter<SplitListRecViewAdapter.ViewHolder> {

    private ArrayList<Swimmer.Lap> laps = new ArrayList<>();

    public SplitListRecViewAdapter () {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lap_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position != 0) {
            holder.splitNumber.setText("" + position);
            holder.split.setText(laps.get(position).getSplitClockTime());
            holder.overallTime.setText(laps.get(position).getOverallClockTime());
        }
    }

    @Override
    public int getItemCount() {
        return laps.size();
    }
    public void setLaps (ArrayList<Swimmer.Lap> laps) {
        this.laps = laps;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView splitNumber;
        private TextView split;
        private TextView overallTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            splitNumber = itemView.findViewById(R.id.splitNumber);
            split = itemView.findViewById(R.id.split);
            overallTime = itemView.findViewById(R.id.overallTime);
        }
    }
}
