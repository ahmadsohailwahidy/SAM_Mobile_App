package com.example.temperatureconverter;


public class TemperatureUtils {

    public static double celceiusToFarenheit(double celceius) {

        return (celceius * 9 / 5) + 32;

    }

    public static double farenheitToCelceius(double farenheit) {

        return (farenheit - 32) * 5 / 9;

    }
    
}
