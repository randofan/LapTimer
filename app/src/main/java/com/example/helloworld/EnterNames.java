package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EnterNames extends AppCompatActivity {

    private RecyclerView swimmerRecview;
    private Button nextBtn;
    private SwimmerRecViewAdapter adapter;
    ArrayList<Swimmer> swimmers;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // TODO recreate RecyclerView
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_names);

        int numberOfSwimmers = getIntent().getIntExtra("NUMBER_OF_SWIMMERS", 0);
        swimmers = new ArrayList<>();
        for (int i = 0; i < numberOfSwimmers; i++) { //populates the arraylist of swimmers with laps arraylist
            swimmers.add(new Swimmer("Swimmer " + (i + 1), new ArrayList<>()));
        }

        nextBtn = findViewById(R.id.actionBtn);

        swimmerRecview = findViewById(R.id.swimmerRecView);
        adapter = new SwimmerRecViewAdapter(this, 1, swimmers);
        swimmerRecview.setAdapter(adapter);

        swimmerRecview.setLayoutManager(new LinearLayoutManager(this)); // TODO switch to grid for horizontal

        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EnterNames.this, Timer.class);
            intent.putParcelableArrayListExtra("SWIMMERS", swimmers);
            startActivity(intent);
        });
    }
}