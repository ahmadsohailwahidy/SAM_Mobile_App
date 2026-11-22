package com.example.temperatureconverter;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;

public class ResultActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        Button backButton = findViewById(R.id.btnBack);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            finish();
        });





    }
    
}
