package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Spinner numberOfStudents;
    private Button nextBtn;
    private int numberOfSwimmers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO add preview text here instead of having textview
        numberOfStudents = findViewById(R.id.dropdown_menu);
        numberOfStudents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberOfSwimmers = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EnterNames.class);
            intent.putExtra("NUMBER_OF_SWIMMERS", numberOfSwimmers);
            startActivity(intent);
        });
    }
}