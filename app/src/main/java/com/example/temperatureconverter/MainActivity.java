package com.example.temperatureconverter;

import android.content.Intent;
import android.os.Bundle;

import android.widget.RadioGroup;

import android.widget.EditText;

import android.widget.RadioButton;

import android.widget.Button;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String ORIGINAL = "Exact or Original Temperature";

    public static final String CONVERTED = "Converted Tempereature";

    public static final String SOURCE = "source";

    public static final String TARGET = "target";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button showResult = findViewById(R.id.btnOpenResult);

        EditText editTemperature = findViewById(R.id.editTemperature);

        RadioGroup radioConversion = findViewById(R.id.radioConversion);

        RadioButton celceiusConverter = findViewById(R.id.celceiusConverter);

        RadioButton farenheitConverter = findViewById(R.id.farenheitConverter);



        showResult.setOnClickListener(v -> {

            String input = editTemperature.getText().toString().trim();

            if (input.isEmpty()) {
                editTemperature.setError("please enter temperature");
                return;
            }

            double temperature;

            try {
                temperature = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                editTemperature.setError("Invalid temperature");
                
                return;
            }

            if (!Double.isFinite(temperature)) {

                editTemperature.setError("Invalid temperature");

                return;
            }

            int selectedConversion = radioConversion.getCheckedRadioButtonId();

            double result;

            String source;
            String target;
           
                if (selectedConversion == R.id.celceiusConverter) {

                    result = (temperature - 32) * 5 / 9;

                    source = "°F";
                    target = "°C";
                } else if (selectedConversion == R.id.farenheitConverter) {

                    result = (temperature * 9 / 5) + 32;

                    source="°C";
                    target="°F";
                } else {
                    Toast.makeText(this, "Please select conversion type", Toast.LENGTH_SHORT).show();

                    return;
                }
           



            Intent intent = new Intent(this, ResultActivity.class);

            intent.putExtra(ORIGINAL, temperature);

            intent.putExtra(CONVERTED, result);

            intent.putExtra(SOURCE, source);

            intent.putExtra(TARGET, target);

            startActivity(intent);

        });


        


    }
}
