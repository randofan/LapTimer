package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EnterNames extends AppCompatActivity {

    private RecyclerView swimmerRecview;
    private Button nextBtn;
    private SwimmerRecViewAdapter adapter;
    private RelativeLayout container;
    ArrayList<Swimmer> swimmers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_names);

        int numberOfSwimmers = getIntent().getIntExtra("NUMBER_OF_SWIMMERS", 0);
        if (numberOfSwimmers != 0) {
            swimmers = new ArrayList<>();
            for (int i = 0; i < numberOfSwimmers; i++) { // populates the arraylist of swimmers with laps arraylist
                swimmers.add(new Swimmer("Swimmer " + (i + 1), new ArrayList<>()));
            }
        }
        else {
            swimmers = getIntent().getParcelableArrayListExtra("SWIMMERS");
        }

        nextBtn = findViewById(R.id.actionBtn);
        container = findViewById(R.id.container);
        swimmerRecview = findViewById(R.id.swimmerRecView);

        adapter = new SwimmerRecViewAdapter(this, SwimmerRecViewAdapter.ENTER_NAMES, swimmers);
        swimmerRecview.setHasFixedSize(true);
        swimmerRecview.setAdapter(adapter);
        swimmerRecview.setLayoutManager(new LinearLayoutManager(this));

        nextBtn.setOnClickListener(v -> {
            container.measure(0,0);

            Intent intent = new Intent(EnterNames.this, Timer.class);
            intent.putParcelableArrayListExtra("SWIMMERS", swimmers);

            intent.putExtra("HEIGHT", container.getHeight() - dpToPx(R.dimen.margin_bottom, this) - spToPx(R.dimen.text_size, this));
            startActivity(intent);
        });
    }
    private static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
    private static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}