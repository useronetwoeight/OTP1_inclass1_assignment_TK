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