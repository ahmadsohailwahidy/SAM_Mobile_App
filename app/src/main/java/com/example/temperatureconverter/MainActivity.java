package com.example.temperatureconverter;

import android.content.Intent;
import android.os.Bundle;

import android.widget.RadioGroup;

import android.widget.EditText;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button showResult = findViewById(R.id.btnOpenResult);

        EditText editTemperature = findViewById(R.id.editTemperature);

        RadioGroup radioConversion = findViewById(R.id.radioConversion);

        



        showResult.setOnClickListener(v -> {

            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);

        });


        


    }
}
