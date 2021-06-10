package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class EnterNames extends AppCompatActivity {

    private RecyclerView swimmerRecview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_names);
        int numberOfSwimmers = getIntent().getIntExtra("NUMBER_OF_SWIMMERS", 0);

        swimmerRecview = findViewById(R.id.swimmerRecView);

        ArrayList<Swimmer> swimmers = new ArrayList<>();
        for (int i = 0; i < numberOfSwimmers; i++) {
            swimmers.add(new Swimmer());
        }

        SwimmerRecViewAdapter adapter = new SwimmerRecViewAdapter();
        adapter.setSwimmers(swimmers);
        swimmerRecview.setAdapter(adapter);

        //if (swimmers.size() < 4) {
            swimmerRecview.setLayoutManager(new LinearLayoutManager(this));
        //}
        //else {
        //    swimmerRecview.setLayoutManager(new GridLayoutManager(this, 2));
        //}
    //TODO add start timer button at bottom not in recycle view
    }
}