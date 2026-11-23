package com.example.temperatureconverter;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;

import java.util.Locale;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;



import android.widget.TextView;

public class ResultActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        Button backButton = findViewById(R.id.btnBack);

        TextView txtOriginal = findViewById(R.id.txtOriginal);

        TextView txtResult = findViewById(R.id.txtResult);

        TextView txtConversion = findViewById(R.id.txtConversion);

        Intent intent = getIntent();

        if (!intent.hasExtra(MainActivity.ORIGINAL)
            || !intent.hasExtra(MainActivity.CONVERTED)
            || !intent.hasExtra(MainActivity.SOURCE)
            || !intent.hasExtra(MainActivity.TARGET)) {
               
                finish();
                return;
            }

        double original = intent.getDoubleExtra(MainActivity.ORIGINAL, 0);

        double converted = intent.getDoubleExtra(MainActivity.CONVERTED, 0);

        String source = intent.getStringExtra(MainActivity.SOURCE);

        String target = intent.getStringExtra(MainActivity.TARGET);

        DecimalFormat formatter = new DecimalFormat(
            "0.##", DecimalFormatSymbols.getInstance(Locale.US)
        );
        

        txtOriginal.setText(
            formatter.format(original) + " " + source
        );

        txtResult.setText(
            formatter.format(converted) + " " + target
        );

        String conversionType;

        if(source.equals("°C")) {

            conversionType = "Celceius to Farenheit";
        } else {

            conversionType = "Farenheit to Celceius";
        }

        txtConversion.setText(conversionType);
        




        backButton.setOnClickListener(v -> {
            // Intent intent = new Intent(this, MainActivity.class);
            finish();
        });

    }

}
