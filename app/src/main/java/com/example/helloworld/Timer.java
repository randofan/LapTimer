package com.example.helloworld;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;

import java.util.ArrayList;

public class Timer extends AppCompatActivity {

    private Button startBtn;
    private RecyclerView swimmerRecview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ArrayList<Swimmer> swimmers = getIntent().getParcelableArrayListExtra("SWIMMERS");

        swimmerRecview = findViewById(R.id.swimmerRecView);
        SwimmerRecViewAdapter adapter = new SwimmerRecViewAdapter(this, 2);
        adapter.setSwimmers(swimmers);

        swimmerRecview.setAdapter(adapter);
        swimmerRecview.setLayoutManager(new LinearLayoutManager(this));

        startBtn = findViewById(R.id.actionBtn);
        startBtn.setText("Start Timer");
        startBtn.setOnClickListener(v -> {
            if (!startBtn.getText().toString().equals("Stop Timer")) { //start
                startBtn.setText("Stop Timer");
                //adapter.setTimer(true);
            }
            else { //stop
                startBtn.setText("Reset Timer");
                //adapter.setTimer(false);
            }
        });
    }
}
