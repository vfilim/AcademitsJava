package ru.academits.filimonov.temperature.model;

public class FahrenheitScale implements Scale {
    @Override
    public String getName() {
        return "Fahrenheit";
    }

    @Override
    public double convertToCelsius(double value) {
        return (value - 32) * 5.0 / 9;
    }

    @Override
    public double convertFromCelsius(double value) {
        return (9.0 / 5) * value + 32;
    }
}