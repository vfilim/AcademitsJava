package ru.academits.filimonov.temperature.model;

public class CelsiusScale implements Scale {
    @Override
    public String getName() {
        return "Celsius";
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