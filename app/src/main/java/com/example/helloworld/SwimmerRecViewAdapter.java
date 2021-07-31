package com.example.helloworld;

import android.annotation.SuppressLint;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    private String clockTime;

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

    public void setCurrentTimer(long currentCentiseconds, String clockTime) {
        this.currentCentiseconds = currentCentiseconds;
        this.clockTime = clockTime;
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
        else if (viewType == 3) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swimmer_list_item3, parent, false);
            return new ViewHolder3(view, viewType);
        }
        else  {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swimmer_list_item, parent, false);
            return new ViewHolder1(view, viewType);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
                Toast.makeText(context, "" + currentCentiseconds, Toast.LENGTH_SHORT).show();
                if (currentCentiseconds != 0) {
                    swimmers.get(position).addLaps(currentCentiseconds, clockTime);
                    ((ViewHolder2) holder).txtSplit.setText(clockTime);
                }
            });
        }
        else if (holder instanceof ViewHolder3) { //Swimmer Details
            ((ViewHolder3) holder).txtname.setText(swimmers.get(position).getName());
            ((ViewHolder3) holder).txtlaneNumber.setText(position + 1 + "");

            SplitListRecViewAdapter adapter = new SplitListRecViewAdapter();
            adapter.setLaps(swimmers.get(position).getLaps());

            ((ViewHolder3) holder).splitList.setAdapter(adapter);
            ((ViewHolder3) holder).splitList.setLayoutManager(new LinearLayoutManager(context));

            ((ViewHolder3) holder).expandIBtn.setOnClickListener(v -> {
                if (((ViewHolder3) holder).splitList.getVisibility() != View.VISIBLE) {
                    ((ViewHolder3) holder).splitList.setVisibility(View.VISIBLE);
                    ((ViewHolder3) holder).expandIBtn.setImageDrawable(this.context.getDrawable(R.drawable.ic_expand_item_up));
                }
                else {
                    ((ViewHolder3) holder).splitList.setVisibility(View.GONE);
                    ((ViewHolder3) holder).expandIBtn.setImageDrawable(this.context.getDrawable(R.drawable.ic_expand_item_down));
                }

            });
        }
    }

    public class ViewHolder3 extends RecyclerView.ViewHolder {

        private TextView txtlaneNumber;
        private TextView txtname;
        private ImageButton expandIBtn;
        private RecyclerView splitList;

        public ViewHolder3 (@NonNull View itemView, int viewType) {
            super(itemView);
            txtlaneNumber = itemView.findViewById(R.id.txtlaneNumber);
            txtname = itemView.findViewById(R.id.txtName);
            expandIBtn = itemView.findViewById(R.id.expandIBtn);
            splitList = itemView.findViewById(R.id.splitList);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        private TextView txtlaneNumber;
        private TextView txtName;
        private Button splitBtn;
        private TextView txtSplit;

        public ViewHolder2(@NonNull View itemView, int viewType) {
            super(itemView);
            txtlaneNumber = itemView.findViewById(R.id.txtlaneNumber);
            txtName = itemView.findViewById(R.id.txtName);
            splitBtn = itemView.findViewById(R.id.splitBtn);
            txtSplit = itemView.findViewById(R.id.txtSplit);
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
