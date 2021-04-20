package ru.academits.filimonov.temperature.model;

public class CelsiusScale extends Scale {
    public CelsiusScale() {
        super("Celsius");
    }

    @Override
    public double convertToCelsius(double value) {
        return value;
    }

    @Override
    public double convertFromCelsius(double value) {
        return value;
    }
}