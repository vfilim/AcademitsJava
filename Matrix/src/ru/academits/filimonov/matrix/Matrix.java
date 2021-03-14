package ru.academits.filimonov.matrix;

import ru.academits.filimonov.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount < 1) {
            throw new IllegalArgumentException("Rows count can't be < 1, it is " + rowsCount);
        }

        if (columnsCount < 1) {
            throw new IllegalArgumentException("Columns count can't be < 1, it is " + columnsCount);
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < matrix.rows.length; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("The rows count can't be 0");
        }

        rows = new Vector[numbers.length];

        int columnsCount = 0;

        for (double[] row : numbers) {
            if (columnsCount < row.length) {
                columnsCount = row.length;
            }
        }

        if (columnsCount == 0) {
            throw new IllegalArgumentException("The columns count can't be 0");
        }

        for (int i = 0; i < numbers.length; i++) {
            rows[i] = new Vector(columnsCount);

            if (numbers[i].length == 0) {
                continue;
            }

            rows[i].add(new Vector(numbers[i]));
        }
    }

    public Matrix(Vector... rows) {
        if (rows.length == 0) {
            throw new IllegalArgumentException("Rows count can't be 0");
        }

        int coordinatesCount = 0;

        for (Vector row : rows) {
            if (row.getSize() > coordinatesCount) {
                coordinatesCount = row.getSize();
            }

        }

        Vector[] matrixRows = new Vector[rows.length];

        for (int i = 0; i < rows.length; i++) {
            matrixRows[i] = new Vector(coordinatesCount);
            matrixRows[i].add(new Vector(rows[i]));
        }

        this.rows = matrixRows;
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getSize();
    }

    public Vector getRow(int index) {
        if (index > getRowsCount() - 1 || index < 0) {
            throw new IndexOutOfBoundsException("The requested row index " + index + " can't be less than 0 or be more than matrix rows count " + getRowsCount());
        }

        return rows[index];
    }

    public void setRow(int index, Vector row) {
        if (row.getLength() != getColumnsCount()) {
            throw new IllegalArgumentException("The new row length " + row.getLength() + " can't be different from old one's length " + getColumnsCount());
        }

        if (index > getRowsCount() - 1 || index < 0) {
            throw new IndexOutOfBoundsException("The new row index " + index + " can't be less than 0 or be be more than matrix rows count " + getRowsCount());
        }

        rows[index] = new Vector(row);
    }

    public Vector getColumn(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index can't be < 0 (it is " + index + ")");
        }

        if (index > rows[0].getSize() - 1) {
            throw new IndexOutOfBoundsException("The index " + index + " can't be bigger than columns count " + rows[0].getSize());
        }

        Vector column = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            column.setCoordinate(i, rows[i].getCoordinate(index));
        }

        return column;
    }

    public void transpose() {
        int newRowsCount = getColumnsCount();

        Vector[] newRows = new Vector[newRowsCount];

        for (int i = 0; i < newRowsCount; i++) {
            newRows[i] = getColumn(i);
        }

        rows = newRows;
    }

    public void multiplyByNumber(double number) {
        for (Vector row : rows) {
            row.multiplyOnNumber(number);
        }
    }

    public Vector multiplyByVector(Vector vector) {
        int vectorSize = vector.getSize();

        if (vectorSize != getColumnsCount()) {
            throw new IllegalArgumentException("The count of matrix columns must be equal to the vector coordinates count, they are "
                    + getRowsCount() + " and " + vectorSize);
        }

        int resultSize = getRowsCount();

        double[] coordinates = new double[resultSize];

        for (int i = 0; i < resultSize; i++) {
            coordinates[i] = Vector.getScalarMultiplication(rows[i], vector);
        }

        return new Vector(coordinates);
    }

    public double getDeterminant() {
        if (getRowsCount() != getColumnsCount()) {
            throw new IllegalStateException(String.format("The matrix must be square for getting determinant. It is %dx%d now", getRowsCount(), getColumnsCount()));
        }

        double[][] matrixArray = new double[getRowsCount()][getColumnsCount()];

        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                matrixArray[i][j] = rows[i].getCoordinate(j);
            }
        }

        return getDeterminant(matrixArray);
    }

    private static double[][] getMinorMatrix(double[][] matrix, int elementJ) {
        int minorRank = matrix.length - 1;
        double[][] minorMatrix = new double[minorRank][minorRank];

        for (int j = 0; j < minorRank; j++) {
            if (j < elementJ) {
                for (int i = 1; i <= minorRank; i++) {
                    minorMatrix[i - 1][j] = matrix[i][j];
                }
            } else {
                for (int i = 1; i <= minorRank; i++) {
                    minorMatrix[i - 1][j] = matrix[i][j + 1];
                }
            }
        }

        return minorMatrix;
    }

    private static double getDeterminant(double[][] matrix) {
        int matrixRank = matrix.length;

        if (matrixRank == 1) {
            return matrix[0][0];
        }

        if (matrixRank == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        int determinant = 0;

        for (int j = 0; j < matrixRank; j++) {
            double element = matrix[0][j];
            determinant += (int) Math.pow(-1, j) * element * getDeterminant(getMinorMatrix(matrix, j));
        }

        return determinant;
    }

    public void add(Matrix matrix) {
        if (getRowsCount() != matrix.getRowsCount() || getColumnsCount() != matrix.getColumnsCount()) {
            throw new IllegalArgumentException("The matrices sizes must be the same. They are (" + getRowsCount()
                    + ", " + getColumnsCount() + ") and (" + matrix.getRowsCount() + ", " + getColumnsCount());
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        if (getRowsCount() != matrix.getRowsCount() || getColumnsCount() != matrix.getColumnsCount()) {
            throw new IllegalArgumentException("The matrices sizes must be the same. They are (" + getRowsCount()
                    + ", " + getColumnsCount() + ") and (" + matrix.getRowsCount() + ", " + getColumnsCount());
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount() || matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException(String.format("The matrices must have the same sizes, they are %dx%d and %dx%d now.",
                    matrix1.getRowsCount(), matrix1.rows[1].getSize(), matrix2.getRowsCount(), matrix2.rows[1].getSize()));
        }

        Matrix sum = new Matrix(matrix1);

        sum.add(matrix2);

        return sum;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount() || matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException(String.format("The matrices must have the same sizes, they are %dx%d and %dx%d now.",
                    matrix1.getRowsCount(), matrix1.rows[1].getSize(), matrix2.getRowsCount(), matrix2.rows[1].getSize()
            ));
        }

        Matrix difference = new Matrix(matrix1);

        difference.subtract(matrix2);

        return difference;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("The first matrix columns count must be equal to the second matrix rows count, they are "
                    + matrix1.getColumnsCount() + " and " + matrix2.getRowsCount());
        }

        Matrix multiplication = new Matrix(matrix1.getRowsCount(), matrix2.getColumnsCount());

        for (int i = 0; i < matrix1.getRowsCount(); i++) {
            for (int j = 0; j < matrix2.getColumnsCount(); j++) {
                multiplication.rows[i].setCoordinate(j, Vector.getScalarMultiplication(matrix1.rows[i], matrix2.getColumn(j)));
            }
        }

        return multiplication;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");

        for (int i = 0; i < rows.length - 1; i++) {
            sb.append(rows[i]);
            sb.append(", ");
        }

        sb.append(rows[rows.length - 1]);
        sb.append("}");

        return sb.toString();
    }
}
