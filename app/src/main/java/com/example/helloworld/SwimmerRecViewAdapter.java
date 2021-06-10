package com.example.helloworld;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SwimmerRecViewAdapter extends RecyclerView.Adapter<SwimmerRecViewAdapter.ViewHolder> {

    private ArrayList<Swimmer> swimmers = new ArrayList<>();

    public SwimmerRecViewAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swimmer_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SwimmerRecViewAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(swimmers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return swimmers.size();
    }

    public void setSwimmers(ArrayList<Swimmer> swimmers) {
        this.swimmers = swimmers;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
