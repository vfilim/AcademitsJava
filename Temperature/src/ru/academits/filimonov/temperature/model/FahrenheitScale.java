package ru.academits.filimonov.temperature.model;

public class FahrenheitScale extends Scale {
    public FahrenheitScale() {
        super("Fahrenheit");
    }

    @Override
    protected double convertToCelsius(double value) {
        return (value - 32) * 5.0 / 9;
    }

    @Override
    protected double convertFromCelsius(double value) {
        return (9.0 / 5) * value + 32;
    }
}