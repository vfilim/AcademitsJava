package ru.academits.filimonov.matrix;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount < 1) {
            throw new IllegalArgumentException("rows count can't be < 1, it is " + rowsCount);
        }

        if (rowsCount < 1) {
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
        if (numbers.length < 1) {
            throw new IllegalArgumentException("Elements count can't be 0");
        }

        rows = new Vector[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            rows[i] = new Vector(numbers[0].length);

            for (int j = 0; j < numbers[0].length; j++) {
                rows[i].setCoordinate(j, numbers[i][j]);
            }
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

            for (int j = 0; j < rows[i].getSize(); j++) {
                matrixRows[i].setCoordinate(j, rows[i].getCoordinate(j));
            }
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
        if (index > getRowsCount() || index < 0) {
            throw new IllegalArgumentException("The requested row index can't be less than 0 or be more than matrix rows count");
        }

        return rows[index];
    }

    public void setRow(Vector row, int index) {
        if (row.getLength() > getColumnsCount()) {
            throw new IllegalArgumentException("The new matrix row can't be longer than old ones. It's length is " + row.getLength());
        }

        if (index > getRowsCount() || index < 0) {
            throw new IllegalArgumentException("The new row index can't be less than 0 or be be more than matrix rows count. It is " + index);
        }

        rows[index] = row;
    }

    public Vector getColumn(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("The index can't be < 0 (it is " + index + ")");
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

    public void multiplyOnNumber(double number) {
        for (Vector row : rows) {
            row.multiplyOnNumber(number);
        }
    }

    public Vector multiplyOnVector(Vector vector) {
        int vectorSize = vector.getSize();

        if (vectorSize != getColumnsCount()) {
            throw new IllegalArgumentException("The count of matrix columns must be equal to the vector coordinates count, they are "
                    + getRowsCount() + " and " + vectorSize);
        }

        double[] coordinates = new double[vectorSize];

        for (int i = 0; i < vectorSize; i++){
            coordinates[i] = Vector.getScalarMultiplication(rows[i], vector);
        }

        rows = new Vector[1];

        return new Vector(coordinates);
    }

    public double getDeterminant() {
        double[][] matrixArray = new double[getRowsCount()][getColumnsCount()];

        for (int i = 0; i < getRowsCount(); i++){
            for (int j = 0; j < getColumnsCount(); j++){
                matrixArray[i][j] = rows[i].getCoordinate(j);
            }
        }

        double determinant = DeterminantCalculator.GetDeterminant(matrixArray);

        return determinant;
    }

    private static class DeterminantCalculator{
        private static double[][] GetMinorMatrix(double[][] matrix, int elementJ)
        {
            int minorRank = matrix.length - 1;
            double[][] minorMatrix = new double[minorRank][minorRank];

            for (int j = 0; j < minorRank; j++)
            {
                if (j < elementJ)
                {
                    for (int i = 1; i <= minorRank; i++)
                    {
                        minorMatrix[i - 1][j] = matrix[i][j];
                    }
                }
                else
                {
                    for (int i = 1; i <= minorRank; i++)
                    {
                        minorMatrix[i - 1][j] = matrix[i][j + 1];
                    }
                }
            }

            return minorMatrix;
        }

        private static double GetDeterminant(double[][] matrix)
        {
            int matrixRank = matrix.length;

            if (matrixRank == 1)
            {
                return matrix[0][0];
            }

            if (matrixRank == 2)
            {
                return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            }

            int determinant = 0;

            for (int j = 0; j < matrixRank; j++)
            {
                double element = matrix[0][j];
                determinant += (int)Math.pow(-1, j) * element * GetDeterminant(GetMinorMatrix(matrix, j));
            }

            return determinant;
        }
    }

    public void addMatrix(Matrix matrix) {
        if (getRowsCount() != matrix.getRowsCount() || getColumnsCount() != matrix.getColumnsCount()) {
            throw new IllegalArgumentException("The matrices sizes must be the same. They are (" + getRowsCount()
                    + ", " + getColumnsCount() + ") and (" + matrix.getRowsCount() + ", " + getColumnsCount());
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtractMatrix(Matrix matrix) {
        if (getRowsCount() != matrix.getRowsCount() || getColumnsCount() != matrix.getColumnsCount()) {
            throw new IllegalArgumentException("The matrices sizes must be the same. They are (" + getRowsCount()
                    + ", " + getColumnsCount() + ") and (" + matrix.getRowsCount() + ", " + getColumnsCount());
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public static Matrix getMatrixSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount() || matrix1.getColumnsCount() != matrix2.getColumnsCount()){
            throw new IllegalArgumentException("The matrices must have the same sizes");
        }

        Matrix sum = new Matrix(matrix1);

        sum.addMatrix(matrix2);

        return sum;
    }

    public static Matrix getMatrixDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount() || matrix1.getColumnsCount() != matrix2.getColumnsCount()){
            throw new IllegalArgumentException("The matrices must have the same sizes");
        }

        Matrix difference = new Matrix(matrix1);

        difference.subtractMatrix(matrix2);

        return difference;
    }

    public static Matrix getMatrixMultiplication(Matrix matrix1, Matrix matrix2) {
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
