package org.example;

public class Utils {
    public static double[][] generateMatrix(int size){
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            double[] row = new double[size];
            for (int j = 0; j < size; j++) {
                row[j] = 1;
            }
            matrix[i] = row;
        }
        return matrix;
    }

    public static double[][] multiply(double[][] matrix1, double[][] matrix2){
        double[][] result = new double[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                double sum = 0;
                for (int k = 0; k < matrix1[0].length; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    public static double[][] extractBlock(double[][] matrix, int startRow, int endRow, int size) {
        double[][] block = new double[endRow - startRow + 1][size];
        int blockIndex = 0;
        for (int i = startRow; i < endRow; i++) {
            double[] tempRow = new double[size];
            for (int j = 0; j < size; j++) {
                tempRow[j] = matrix[i][j];
            }
            block[blockIndex] = tempRow;
            blockIndex++;
        }
        return block;
    }

    public static double[][] addBlock(double[][] partialMatrix, double[][] matrix, int startRow, int endRow){
        double[][] block = matrix;
        int rowIndex = 0;
        for(int i = startRow; i < endRow; i++){
            for(int j = 0; j < matrix[0].length; j++){
                block[i][j] = partialMatrix[rowIndex][j];
            }
            rowIndex++;
        }
        return block;
    }
}
