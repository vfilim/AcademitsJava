package ru.academits.filimonov.temperature.model;

public interface Scale {
    String getName();

    double convertToCelsius(double value);

    double convertFromCelsius(double value);
}