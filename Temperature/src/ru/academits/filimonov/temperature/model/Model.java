package ru.academits.filimonov.temperature.model;

public class Model {
    private GradeType inputGradeType;
    private GradeType outputGradeType;

    public void setInputGradeType(GradeType gradeType) {
        inputGradeType = gradeType;
    }

    public void setOutGradeType(GradeType gradeType) {
        outputGradeType = gradeType;
    }

    public double convert(double inputValue) {
        if (inputGradeType == GradeType.KELVIN) {
            if (outputGradeType == GradeType.CELSIUS) {
                return inputValue - 273.15;
            }

            if (outputGradeType == GradeType.FAHRENHEIT) {
                return (9.0 / 5) * (inputValue - 273.15) + 32;
            }

            return inputValue;
        }

        if (inputGradeType == GradeType.CELSIUS) {
            if (outputGradeType == GradeType.KELVIN) {
                return inputValue + 273.15;
            }

            if (outputGradeType == GradeType.FAHRENHEIT) {
                return (9.0 / 5) * inputValue + 32;
            }

            return inputValue;
        }

        if (outputGradeType == GradeType.KELVIN) {
            return (inputValue - 32) * 5.0 / 9 + 273.15;
        }

        if (outputGradeType == GradeType.CELSIUS) {
            return (inputValue - 32) * 5.0 / 9;
        }

        return inputValue;
    }
}