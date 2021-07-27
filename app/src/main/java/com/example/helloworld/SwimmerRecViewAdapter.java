package com.example.helloworld;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SwimmerRecViewAdapter extends RecyclerView.Adapter {

    private static ArrayList<Swimmer> swimmers = new ArrayList<>();
    private int type;
    private Context context;
    private long currentCentiseconds = 0;

    public SwimmerRecViewAdapter(Context context, int layoutType) {
        type = layoutType;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return swimmers.size();
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    public void setSwimmers(ArrayList<Swimmer> swimmers) {
        this.swimmers = swimmers;

        notifyDataSetChanged();
    }

    public void setCurrentTimer(long currentCentiseconds) {
        this.currentCentiseconds = currentCentiseconds;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swimmer_list_item, parent, false);
            return new ViewHolder1(view, viewType);
        }

        else if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swimmer_list_item2, parent, false);
            return new ViewHolder2(view, viewType);
        }
        else  {
            // TODO change to default setting
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swimmer_list_item, parent, false);
            return new ViewHolder1(view, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder1) { //Enter Names
            ((ViewHolder1) holder).txtlaneNumber.setText(position + 1 + "");
            ((ViewHolder1) holder).editName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().isEmpty()) {
                        swimmers.get(position).setName(s.toString());
                    }
                }
            });
        }
        else if (holder instanceof ViewHolder2) { //Timer
            ((ViewHolder2) holder).txtName.setText(swimmers.get(position).getName());
            ((ViewHolder2) holder).txtlaneNumber.setText(position + 1 + "");

            ((ViewHolder2) holder).splitBtn.setOnClickListener(v -> {
                Toast.makeText(context, "Split", Toast.LENGTH_SHORT).show();
                swimmers.get(position).addLaps(currentCentiseconds);
            });
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        private TextView txtlaneNumber;
        private TextView txtName;
        private Button splitBtn;

        public ViewHolder2(@NonNull View itemView, int viewType) {
            super(itemView);
            txtlaneNumber = itemView.findViewById(R.id.txtlaneNumber);
            txtName = itemView.findViewById(R.id.txtName);

            splitBtn = itemView.findViewById(R.id.splitBtn);
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        private EditText editName;
        private TextView txtlaneNumber;

        public ViewHolder1(@NonNull View itemView, int viewType) {
            super(itemView);
            editName = itemView.findViewById(R.id.editName);
            txtlaneNumber = itemView.findViewById(R.id.txtlaneNumber);
        }
    }
}
