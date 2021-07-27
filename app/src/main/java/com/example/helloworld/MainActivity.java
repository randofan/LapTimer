package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerSwimmers;
    private Button nextBtn;
    private int numberOfSwimmers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO add preview text here instead of having textview
        spinnerSwimmers = findViewById(R.id.dropdown_menu);
        spinnerSwimmers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberOfSwimmers = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Please enter the number of swimmers competing", Toast.LENGTH_SHORT).show();
            }
        });

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(v -> {
            if (numberOfSwimmers != 0) {
                Intent intent = new Intent(MainActivity.this, EnterNames.class);
                intent.putExtra("NUMBER_OF_SWIMMERS", numberOfSwimmers);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Please enter the number of swimmers competing", Toast.LENGTH_SHORT).show();
            }
        });
    }
}