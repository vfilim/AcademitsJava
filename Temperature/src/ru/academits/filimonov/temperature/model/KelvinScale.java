package ru.academits.filimonov.temperature.model;

public class KelvinScale extends Scale{
    public KelvinScale(){
        super("Kelvin");
    }

    @Override
    protected double convertToCelsius(double value) {
        return value - 273.15;
    }

    @Override
    protected double convertFromCelsius(double value) {
        return value + 273.15;
    }
}