package com.example.helloworld;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.helloworld.databinding.ActivitySwimmerDetailsBinding;

import java.util.ArrayList;

public class SwimmerDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swimmer_details);
        ArrayList<Swimmer> swimmers = getIntent().getParcelableArrayListExtra("SWIMMERS");



    }
}