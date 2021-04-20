package ru.academits.filimonov.temperature.model;

import java.util.ArrayList;

public interface ScaleConverterModel {
    ArrayList<Scale> getScalesList();

    void setInputScale(Scale scale);

    void setOutScale(Scale scale);

    double convert(double value);
}