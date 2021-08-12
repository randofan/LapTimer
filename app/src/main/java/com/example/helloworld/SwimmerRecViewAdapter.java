package com.example.helloworld;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class SwimmerRecViewAdapter extends RecyclerView.Adapter implements TimerInterface {

    private static ArrayList<Swimmer> swimmers = new ArrayList<>();
    private final int type;
    private final Context context;
    private static int topMargin;
    private static int bottomMargin;
    private static int totalHeight;

    public SwimmerRecViewAdapter(Context context, int layoutType, ArrayList<Swimmer> swimmers) {
        this.type = layoutType;
        this.context = context;
        SwimmerRecViewAdapter.swimmers = swimmers;
    }

    @Override
    public int getItemCount() {
        if (swimmers != null) return swimmers.size();
        else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    public void setSize (int topMargin, int bottomMargin) {
        SwimmerRecViewAdapter.topMargin = topMargin;
        SwimmerRecViewAdapter.bottomMargin = bottomMargin;
        SwimmerRecViewAdapter.totalHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        notifyDataSetChanged();
    }
    private static int getViewHolderHeight() {
        int temp = totalHeight - topMargin - bottomMargin;
//        Log.d("total", "" + totalHeight);   //2088
//        Log.d("top", "" + topMargin);       //542
//        Log.d("bottom", "" + bottomMargin); //132
//        Log.d("usable", "" + temp);         //1414
        return ((temp) / swimmers.size()); // TODO size doesn't work properly
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof ViewHolder1) { //Enter Names
            ((ViewHolder1) holder).txtlaneNumber.setText(position + 1 + "");
            if (position == 0) {
                ((ViewHolder1) holder).editName.setHint("Participant Name (Optional)");
            }
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
            ((ViewHolder2) holder).parent.getLayoutParams().height = getViewHolderHeight();
            ((ViewHolder2) holder).txtName.setText(swimmers.get(position).getName());
            ((ViewHolder2) holder).txtlaneNumber.setText(position + 1 + "");
            ((ViewHolder2) holder).splitBtn.setOnClickListener(v -> {
                if (currentCentiseconds.longValue() != 0) {
                    swimmers.get(position).addLaps(currentCentiseconds.longValue());

                    int seconds = (int) (currentCentiseconds.longValue() / 100);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;
                    int centiseconds =  (int) (currentCentiseconds.longValue() % 100);
                    ((ViewHolder2) holder).txtSplit.setText("Lap "  + swimmers.get(position).getExistingLaps().size() + ": " + String.format("%02d:%02d.%02d", minutes,seconds,centiseconds));
                    ((ViewHolder2) holder).txtSplit.setVisibility(View.VISIBLE);
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

            ((ViewHolder3) holder).parent.setOnClickListener(v -> {
                if (((ViewHolder3) holder).splits.getVisibility() != View.VISIBLE) {
                    ((ViewHolder3) holder).splits.setVisibility(View.VISIBLE);
                    ((ViewHolder3) holder).expandImg.setRotation(180);
                }
                else {
                    ((ViewHolder3) holder).splits.setVisibility(View.GONE);
                    ((ViewHolder3) holder).expandImg.setRotation(0);
                }

            });
        }
    }

    public class ViewHolder3 extends RecyclerView.ViewHolder {

        private TextView txtlaneNumber;
        private TextView txtname;
        private ImageView expandImg;
        private RecyclerView splitList;
        private CardView parent;
        private RelativeLayout splits;

        public ViewHolder3 (@NonNull View itemView, int viewType) {
            super(itemView);
            txtlaneNumber = itemView.findViewById(R.id.txtlaneNumber);
            parent = itemView.findViewById(R.id.parent);
            txtname = itemView.findViewById(R.id.txtName);
            expandImg = itemView.findViewById(R.id.expandImg);
            splitList = itemView.findViewById(R.id.splitList);
            splits = itemView.findViewById(R.id.splits);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        private TextView txtlaneNumber;
        private TextView txtName;
        private Button splitBtn;
        private TextView txtSplit;
        private CardView parent;

        public ViewHolder2(@NonNull View itemView, int viewType) {
            super(itemView);
            txtlaneNumber = itemView.findViewById(R.id.txtlaneNumber);
            txtName = itemView.findViewById(R.id.txtName);
            splitBtn = itemView.findViewById(R.id.splitBtn);
            txtSplit = itemView.findViewById(R.id.txtSplit);
            parent = itemView.findViewById(R.id.parent);
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        private EditText editName;
        private TextView txtlaneNumber;
        private CardView parent;

        public ViewHolder1(@NonNull View itemView, int viewType) {
            super(itemView);
            editName = itemView.findViewById(R.id.editName);
            txtlaneNumber = itemView.findViewById(R.id.txtlaneNumber);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
