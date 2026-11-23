package com.example.temperatureconverter;

import android.content.Intent;
import android.os.Bundle;

import android.widget.RadioGroup;

import android.widget.EditText;

import android.widget.RadioButton;

import android.widget.Button;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_TEMPERATURE = "state_temperature";
    private static final String STATE_CONVERSION = "state_conversion";

    private EditText editTemperature;
    private RadioGroup radioConversion;
    

    private static final String TAG = "MainActivity";

    public static final String ORIGINAL = "Exact or Original Temperature";

    public static final String CONVERTED = "Converted Tempereature";

    public static final String SOURCE = "source";

    public static final String TARGET = "target";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Log.d(TAG, "onCreate called");

        setContentView(R.layout.activity_main);

        Button showResult = findViewById(R.id.btnOpenResult);

        editTemperature = findViewById(R.id.editTemperature);

        radioConversion = findViewById(R.id.radioConversion);

        RadioButton celceiusConverter = findViewById(R.id.celceiusConverter);

        RadioButton farenheitConverter = findViewById(R.id.farenheitConverter);

        TemperatureUtils temp = new TemperatureUtils();



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

                    result = temp.farenheitToCelceius(temperature);

                    source = "°F";
                    target = "°C";
                } else if (selectedConversion == R.id.farenheitConverter) {

                    result = temp.celceiusToFarenheit(temperature);

                    source="°C";
                    target="°F";
                } else {
                    Toast.makeText(this, "Please select conversion type", Toast.LENGTH_SHORT).show();

                    return;
                }
           



            Intent intent = new Intent(MainActivity.this, ResultActivity.class);

            intent.putExtra(ORIGINAL, temperature);

            intent.putExtra(CONVERTED, result);

            intent.putExtra(SOURCE, source);

            intent.putExtra(TARGET, target);

            startActivity(intent);

        });


        


    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        String temperature = editTemperature.getText().toString();

        int selectedConversion = radioConversion.getCheckedRadioButtonId();

        state.putString(STATE_TEMPERATURE, temperature);

        state.putInt(STATE_CONVERSION, selectedConversion);

        Log.d(TAG, "activity state saved");
    }

    @Override
    protected void onRestoreInstanceState(Bundle restoreState) {
        super.onRestoreInstanceState(restoreState);

        String temperature = restoreState.getString(STATE_TEMPERATURE, "");

        int selectedConversion = restoreState.getInt(STATE_CONVERSION, R.id.celceiusConverter);

        editTemperature.setText(temperature);
        radioConversion.check(selectedConversion);

        Log.d(TAG, "Activity state restored");
    }

    // @Override
    // protected void onResume(){
    //     super.onResume();
    //     Log.d(TAG, "onResume Called");
    // }

    // @Override
    // protected void onStart(){
    //     super.onStart();
    //     Log.d(TAG, "onStart Called");
    // }

    // @Override
    // protected void onPause(){
    //     super.onPause();
    //     Log.d(TAG, "onPause Called");
    // }

    // @Override
    // protected void onStop(){
    //     super.onStop();
    //     Log.d(TAG, "onStop Called");
    // }

    // @Override
    // protected void onRestart(){
    //     super.onRestart();
    //     Log.d(TAG, "onRestart Called");
    // }

    // @Override
    // protected void onDestroy(){
    //     super.onDestroy();
    //     Log.d(TAG, "onDestroy Called");
    // }


}
