package raz.inteview_test.everest.brynjolf.util;


//import java.util.function.Function;

import raz.inteview_test.everest.brynjolf.room.Element;

public class MatrixUtil {

    static final String delimiter = "  ";

    public static <T> void showMatrix(T[][] matrix) {
        if (matrix == null) {
            System.out.println("Matrix is null");
            return;
        }

        int mainArrElementsNum = matrix.length;

        for (int i = 0; i < mainArrElementsNum; i++) {
            T[] subArray = matrix[i];
            for (int j = 0; j < subArray.length; j++) {
                System.out.print(matrix[i][j]);
                if (j != subArray.length - 1)
                    System.out.print(delimiter);
            }
            System.out.println();
        }
    }

    public static String prettyPrint(Element[][] matrix) {
        StringBuilder sb = new StringBuilder();
        if (matrix == null) {
            sb.append("Matrix is null");
            return sb.toString();
        }

        int matrixArraysNum = matrix.length;

        StringBuilder line;
        Element[] subArray;
        Element element;

        for (int i = 0; i < matrixArraysNum; i++) {
            line = new StringBuilder();
            subArray = matrix[i];
            for (int j = 0; j < subArray.length; j++) {
                element = matrix[i][j];
                line.append(element.prettyPrint());
                if (j != subArray.length - 1)
                    line.append(delimiter);
            }
            sb.append(line);
            if (i != matrixArraysNum - 1) {
                //if not on last line
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    public static <T> void initMatrixWithValue(T[][] matrix, T val) {
        if (matrix == null) {
            throw new IllegalArgumentException("The matrix can not be null");
        }

        for (int i = 0; i < matrix.length; i++) {
            T[] oneLine = matrix[i];
            for (int j = 0; j < oneLine.length; j++) {
                oneLine[j] = val;
            }
        }
    }

}
