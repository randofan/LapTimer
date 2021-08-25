package com.davsong.laptimer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerSwimmers;
    private Button nextBtn;
    private int numberOfSwimmers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerSwimmers = findViewById(R.id.dropdown_menu);
        spinnerSwimmers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberOfSwimmers = position; //position = 0 is ' ' so numbers = position
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                numberOfSwimmers = 0;
            }
        });

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(v -> {
            if (numberOfSwimmers != 0) { //reject is nothing is selected
                Intent intent = new Intent(MainActivity.this, EnterNames.class);
                intent.putExtra("NUMBER_OF_SWIMMERS", numberOfSwimmers);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Please enter the number of swimmers competing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.settingsMenu:
//                startActivity(new Intent(MainActivity.this, Settings.class));
//                return true;
            case R.id.instructionsMenu:
                startActivity(new Intent(MainActivity.this, Instructions.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}