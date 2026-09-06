package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class TemperatureConverterTest {
    @Test
    public void testFahrenheitToCelsius_freezingPoint() {
        Assertions.assertEquals(0.0, converter.fahrenheitToCelsius(32), 0.01);
    }

    @Test
    public void testFahrenheitToCelsius_boilingPoint() {
        Assertions.assertEquals(100.0, converter.fahrenheitToCelsius(212), 0.01);
    }

    @Test
    public void testFahrenheitToCelsius_bodyTemperature() {
        Assertions.assertEquals(37.0, converter.fahrenheitToCelsius(98.6), 0.01);
    }

    @Test
    public void testFahrenheitToCelsius_negativeValue() {
        Assertions.assertEquals(-40.0, converter.fahrenheitToCelsius(-40), 0.01);
    }
}