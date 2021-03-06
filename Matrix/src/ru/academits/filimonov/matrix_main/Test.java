package ru.academits.filimonov.matrix_main;

import ru.academits.filimonov.matrix.Matrix;
import ru.academits.filimonov.vector.Vector;

public class Test {
    public static void main(String[] args) {
        double[][] rows = {{1, 2}, {4, 5}, {6, 7}};

        Matrix matrix1 = new Matrix(rows);

        matrix1.transpose();

        System.out.println("The first matrix is " + matrix1);

        Vector vector1 = new Vector(1.0, 5.0, 8.0);
        Vector vector2 = new Vector(7.0, 9.0, 10.0, 3.0);

        Matrix matrix2 = new Matrix(vector1, vector2);

        System.out.println("The second matrix is " + matrix2);

        Matrix matrix3 = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        System.out.println("The third matrix is " + matrix2);

        Vector vectorMultiplication = matrix3.multiplyByVector(vector1);

        System.out.println("The product of third matrix and vector multiplication is " + vectorMultiplication);

        Matrix multiplier1 = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6}
        });
        Matrix multiplier2 = new Matrix(new double[][]{
                {7, 8, 9, 10},
                {11, 12, 13, 14},
                {15, 16, 17, 18}
        });

        System.out.println("The product of two matrix multipliers is " + Matrix.getProduct(multiplier1, multiplier2));

        Matrix matrix4 = new Matrix(new double[][]{
                {4, 2, 4, -2},
                {-5, 5, 6, -3},
                {-6, 3, 8, -5},
                {1, 9, 4, 2}
        });

        System.out.println("The determinant of fourth matrix is " + matrix4.getDeterminant());
    }
}