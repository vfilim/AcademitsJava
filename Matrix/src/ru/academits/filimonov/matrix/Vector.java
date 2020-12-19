package ru.academits.filimonov.matrix;

import java.util.Arrays;

public class Vector {
    private double[] coordinates;

    public Vector(int coordinatesCount) {
        if (coordinatesCount < 1) {
            throw new IllegalArgumentException("Coordinates count can't be < 1, it is " + coordinatesCount);
        }

        coordinates = new double[coordinatesCount];
    }

    public Vector(Vector vector) {
        int coordinatesCount = vector.getSize();

        coordinates = Arrays.copyOf(vector.coordinates, coordinatesCount);
    }

    public Vector(double... coordinates) {
        int coordinatesCount = coordinates.length;

        if (coordinatesCount < 1) {
            throw new IllegalArgumentException("Coordinates count can't be < 1, it is " + coordinatesCount);
        }

        this.coordinates = Arrays.copyOf(coordinates, coordinatesCount);
    }

    public Vector(int vectorCoordinatesCount, double... coordinates) {
        if (vectorCoordinatesCount < 1) {
            throw new IllegalArgumentException("Coordinates count can't be < 1, it is " + vectorCoordinatesCount);
        }

        int coordinatesCount = coordinates.length;

        this.coordinates = new double[vectorCoordinatesCount];

        System.arraycopy(coordinates, 0, this.coordinates, 0, Math.min(coordinatesCount, vectorCoordinatesCount));
    }

    public double getCoordinate(int index) {
        return coordinates[index];
    }

    public void setCoordinate(int index, double value) {
        coordinates[index] = value;
    }

    public int getSize() {
        return coordinates.length;
    }

    public void add(Vector vector) {
        if (vector.getSize() > coordinates.length) {
            double[] newCoordinates = Arrays.copyOf(coordinates, vector.getSize());

            coordinates = newCoordinates;
        }

        for (int i = 0; i < vector.getSize(); i++) {
            coordinates[i] += vector.coordinates[i];
        }
    }

    public void subtract(Vector vector) {
        if (vector.getSize() > coordinates.length) {
            double[] newCoordinates = Arrays.copyOf(coordinates, vector.getSize());

            coordinates = newCoordinates;
        }

        for (int i = 0; i < vector.getSize(); i++) {
            coordinates[i] -= vector.coordinates[i];
        }
    }

    public void multiplyOnNumber(double number) {
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] *= number;
        }
    }

    public void turnOver() {
        multiplyOnNumber(-1);
    }

    public double getLength() {
        double lengthSquare = 0;

        for (double c : coordinates) {
            lengthSquare += Math.pow(c, 2);
        }

        return Math.sqrt(lengthSquare);
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector sum = new Vector(vector1);

        sum.add(vector2);

        return sum;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector dif = new Vector(vector1);

        dif.subtract(vector2);

        return dif;
    }

    public static double getScalarMultiplication(Vector vector1, Vector vector2) {
        double scalar = 0;

        int smallerVectorSize = Math.min(vector1.getSize(), vector2.getSize());

        for (int i = 0; i < smallerVectorSize; i++) {
            scalar += vector1.getCoordinate(i) * vector2.getCoordinate(i);
        }

        return scalar;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{ ");

        int newCoordinatesCount = coordinates.length;

        for (int i = 0; i < newCoordinatesCount - 1; i++) {
            sb.append(coordinates[i]);
            sb.append(", ");
        }

        sb.append(coordinates[newCoordinatesCount - 1]);
        sb.append(" }");

        return sb.toString();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Vector v = (Vector) o;

        if (coordinates.length != v.getSize()) {
            return false;
        }

        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i] != v.getCoordinate(i)) {
                return false;
            }
        }

        return true;
    }

    public int hashCode() {
        int prime = 17;
        int hash = 1;

        for (double c : coordinates) {
            hash = prime * hash + Double.hashCode(c);
        }

        return hash;
    }
}
