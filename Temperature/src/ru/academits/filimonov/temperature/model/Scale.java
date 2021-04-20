package ru.academits.filimonov.temperature.model;

public abstract class Scale {
    private final String name;

    protected Scale(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    protected abstract double convertToCelsius(double value);

    protected abstract double convertFromCelsius(double value);
}