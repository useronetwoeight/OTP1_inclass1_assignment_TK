package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TemperatureConverterTest {

    private TemperatureConverter converter = new TemperatureConverter();

    @Test
    public void testFahrenheitToCelsius_0() {
        Assertions.assertEquals(0.0, converter.fahrenheitToCelsius(32), 0.01);
    }

    @Test
    public void testFahrenheitToCelsius_100() {
        Assertions.assertEquals(100.0, converter.fahrenheitToCelsius(212), 0.01);
    }

    @Test
    public void testFahrenheitToCelsius_50() {
        Assertions.assertEquals(50.0, converter.fahrenheitToCelsius(122), 0.01);
    }
    @Test
    public void testCelsiusToFahrenheit_0() {
        Assertions.assertEquals(0.0, converter.fahrenheitToCelsius(32), 0.01);
    }

    @Test
    public void testCelsiusToFahrenheit_100() {
        Assertions.assertEquals(100.0, converter.fahrenheitToCelsius(212), 0.01);
    }

    @Test
    public void testCelsiusToFahrenheit_50() {
        Assertions.assertEquals(50.0, converter.fahrenheitToCelsius(122), 0.01);
    }
    @Test
    public void testIsExtreme_lowTrue() {
        Assertions.assertTrue(converter.isExtremeTemperature(-41));
    }

    @Test
    public void testIIsExtreme_lowFalse() {
        Assertions.assertFalse(converter.isExtremeTemperature(-40));
    }

    @Test
    public void testIsExtreme_highTrue() {
        Assertions.assertTrue(converter.isExtremeTemperature(51));
    }

    @Test
    public void testIsExtreme_highFalse() {
        Assertions.assertFalse(converter.isExtremeTemperature(50));
    }
}