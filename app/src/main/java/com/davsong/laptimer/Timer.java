package com.davsong.laptimer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Timer extends AppCompatActivity implements TimerInterface {

    private Button actionBtn;
    private RecyclerView swimmerRecview;
    private TextView txtTimer;
    private Handler handler;
    private int minutes, seconds, centiseconds = 0;
    private SwimmerRecViewAdapter adapter;
    private ArrayList<Swimmer> swimmers;
    private int containerHeight;

    private Stopwatch stopwatch = Stopwatch.createUnstarted(
            new Ticker() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public long read() {
                    return android.os.SystemClock.elapsedRealtimeNanos();
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        swimmers = getIntent().getParcelableArrayListExtra("SWIMMERS");
        containerHeight = getIntent().getIntExtra("HEIGHT", 0);

        for (Swimmer swimmer : swimmers) {
            if (swimmer.getExistingLaps().size() > 0) swimmer.getExistingLaps().clear();
        }

        txtTimer =  findViewById(R.id.txtTimer);
        actionBtn = findViewById(R.id.actionBtn);
        swimmerRecview = findViewById(R.id.swimmerRecView);

        adapter = new SwimmerRecViewAdapter(this, SwimmerRecViewAdapter.TIMER, swimmers); // TODO fit to screen
        swimmerRecview.setHasFixedSize(true);
        adapter.setHeight(containerHeight);
        swimmerRecview.setAdapter(adapter);
        swimmerRecview.setLayoutManager(new LinearLayoutManager(this));

        handler = new Handler(); // controls the Runnable object

        actionBtn.setText("Start Timer");
        actionBtn.setOnClickListener(v -> {
            if (actionBtn.getText().toString().equals("Start Timer")) { //start button
                actionBtn.setText("Stop Timer");
                stopwatch.start();
                handler.post(runnable);
            }
            else if (actionBtn.getText().toString().equals("Stop Timer")) { //stop button
                actionBtn.setText("Next");
                stopwatch.stop();
                currentCentiseconds.set(0);
                handler.removeCallbacks(runnable);
            }
            else { //next button
                Intent intent = new Intent(Timer.this, SwimmerDetails.class);
                intent.putParcelableArrayListExtra("SWIMMERS", swimmers);
                startActivity(intent);
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentCentiseconds.set(stopwatch.elapsed(TimeUnit.MILLISECONDS) / 10); //currentCentiseconds is AtomicLong inherited from interface

            seconds = (int) (currentCentiseconds.longValue() / 100); //convert to mm:ss:mm
            minutes = seconds / 60;
            seconds = seconds % 60;
            centiseconds =  (int) (currentCentiseconds.longValue() % 100);

            txtTimer.setText(String.format("%02d:%02d.%02d", minutes,seconds,centiseconds));
            
            handler.postDelayed(runnable, 10); // 10 millisecond delay so the timer goes by 0.01 seconds like stopwatches do
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (stopwatch.isRunning()) stopwatch.stop();
                stopwatch.reset();
                handler.removeCallbacks(runnable);

                Intent intent = new Intent(Timer.this, EnterNames.class);
                intent.putParcelableArrayListExtra("SWIMMERS", swimmers);
                startActivity(intent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
