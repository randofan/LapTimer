package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class SwimmerDetails extends AppCompatActivity {

    private RecyclerView swimmerRecview;
    private SwimmerRecViewAdapter adapter;
    private Button resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swimmer_details);
        ArrayList<Swimmer> swimmers = getIntent().getParcelableArrayListExtra("SWIMMERS");

        swimmerRecview = findViewById(R.id.swimmerRecView);
        adapter = new SwimmerRecViewAdapter(this, 3, swimmers);

        swimmerRecview.setAdapter(adapter);
        swimmerRecview.setLayoutManager(new LinearLayoutManager(this));

        resetBtn = findViewById(R.id.actionBtn);
        resetBtn.setText("Reset");
        resetBtn.setOnClickListener(v -> {
            startActivity(new Intent(SwimmerDetails.this, MainActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.export_data_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.downloadMenu:

                Toast.makeText(this, "Currently unsupported. Maybe take a screenshot instead", Toast.LENGTH_SHORT).show();
                // TODO download .csv file to device


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}