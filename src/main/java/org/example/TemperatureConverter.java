package org.example;

public class TemperatureConverter {
    public double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }
    public double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }
    public boolean isExtremeTemperature(double celsius) {
        return celsius < -40 || celsius > 50;
    }

    public double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    public double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    // symbols are "C", "F" and "K"
    public double convert(double value, String from, String to) {
        double celsius;
        if (from.equals("C")) {
            celsius = value;
        } else if (from.equals("F")) {
            celsius = fahrenheitToCelsius(value);
        } else if (from.equals("K")) {
            celsius = kelvinToCelsius(value);
        } else {
            throw new IllegalArgumentException("Unknown unit: " + from);
        }

        if (celsius < -273.15) {
            throw new IllegalArgumentException("Below absolute zero");
        }

        if (to.equals("C")) {
            return celsius;
        } else if (to.equals("F")) {
            return celsiusToFahrenheit(celsius);
        } else if (to.equals("K")) {
            return celsiusToKelvin(celsius);
        } else {
            throw new IllegalArgumentException("Unknown unit: " + to);
        }
    }

    public double speed(double distanceKm, double timeHours) {
        if (timeHours <= 0) {
            throw new IllegalArgumentException("Time must be positive");
        }
        if (distanceKm < 0) {
            throw new IllegalArgumentException("Distance must not be negative");
        }
        return distanceKm / timeHours;
    }

    public static void main(String[] args) {
        TemperatureConverter converter = new TemperatureConverter();

        double f = 98.6;
        double c = converter.fahrenheitToCelsius(f);
        System.out.println(f + "°F is " + c + "°C");

        double celsius = 100;
        double fahrenheit = converter.celsiusToFahrenheit(celsius);
        System.out.println(celsius + "°C is " + fahrenheit + "°F");

        double kelvin = 300;
        double kelvinToCelsius = converter.kelvinToCelsius(kelvin);
        System.out.println(kelvin + "K is " + kelvinToCelsius + "°C");

        System.out.println("Is 60°C extreme? " + converter.isExtremeTemperature(60));
    }
}