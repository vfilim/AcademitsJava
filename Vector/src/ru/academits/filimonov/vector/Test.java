package ru.academits.filimonov.vector;

public class Test {
    public static void main(String[] args) {
        Vector vector1 = new Vector(new double[]{3.1, 4.0});
        Vector vector2 = new Vector(new double[]{2.1, 5.0, 1.5});

        Vector vector3 = Vector.getSum(vector1, vector2);
        Vector vector4 = Vector.getSum(vector2, vector1);
        Vector vector5 = Vector.getDifference(vector1, vector2);
        Vector vector6 = Vector.getDifference(vector2, vector1);

        System.out.println(vector3.toString() + vector4 + vector5 + vector6 + Vector.getScalarMultiplication(vector1, vector2));

        //Vector wrongVector = new Vector(-3);
    }
}
