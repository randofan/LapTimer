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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_names);
        int numberOfSwimmers = getIntent().getIntExtra("NUMBER_OF_SWIMMERS", 0);

        swimmerRecview = findViewById(R.id.swimmerRecView);

        ArrayList<Swimmer> swimmers = new ArrayList<>();
        for (int i = 0; i < numberOfSwimmers; i++) {
            swimmers.add(new Swimmer("Swimmer" + (i+1)));
        }

        SwimmerRecViewAdapter adapter = new SwimmerRecViewAdapter(this, 1);
        adapter.setSwimmers(swimmers);
        swimmerRecview.setAdapter(adapter);

        swimmerRecview.setLayoutManager(new LinearLayoutManager(this));

        nextBtn = findViewById(R.id.actionBtn);
        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EnterNames.this, Timer.class);
            intent.putParcelableArrayListExtra("SWIMMERS", swimmers);
            startActivity(intent);
        });
    }
}