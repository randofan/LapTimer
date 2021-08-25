package com.davsong.laptimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Instructions extends AppCompatActivity {

    private TextView txtInstructions;
    private TextView txtNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        txtInstructions = findViewById(R.id.txtInstructions);
        txtNote = findViewById(R.id.txtNote);

        txtInstructions.setText("The application is intended to provide the user a means to simultaneously track " +
                "the lap times of multiple participants. It is designed for athletic coaches to measure the " +
                "performance of their athletes. ");

        txtNote.setText("Note: Lap Timer is still in development and more updates are to come. Please feel free to " +
                "voice any of  concerns in your application review.");
    }
}