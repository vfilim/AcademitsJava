package ru.academits.filimonov.temperature.model;

import java.util.ArrayList;
import java.util.Arrays;

public class MyScaleConverterModel implements ScaleConverterModel {
    private final ArrayList<Scale> scalesList;

    private Scale inputScale;
    private Scale outputScale;

    public MyScaleConverterModel(Scale... scales) {
        scalesList = new ArrayList<>(Arrays.asList(scales));
    }

    public ArrayList<Scale> getScalesList() {
        return scalesList;
    }

    public void setInputScale(Scale scale) {
        inputScale = scale;
    }

    public void setOutScale(Scale scale) {
        outputScale = scale;
    }

    public double convert(double value) {
        return outputScale.convertFromCelsius(inputScale.convertToCelsius(value));
    }
}