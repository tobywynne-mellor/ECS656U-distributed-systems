package com.dscoursework.matrix;

import java.util.Arrays;

public class MatrixUtils {
    /**
     * Takes a matrix string turns it into a 2D integer array
     */
    public static int[][] decodeMatrix(String matrixString) {
        return stringToDeep(matrixString);
    }

    /**
     * Takes a 2D integer array and turns it into an encoded string
     */
    public static String encodeMatrix(int[][] matrix) {
        return Arrays.deepToString(matrix);
    }

    /**
     * Turns array.toDeepString() back to array[][]
     * https://stackoverflow.com/questions/22377447/java-multidimensional-array-to-string-and-string-to-array/22428926#22428926
     */
    public static int[][] stringToDeep(String str) {
        int row = 0;
        int col = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '[') {
                row++;
            }
        }
        row--;
        for (int i = 0;; i++) {
            if (str.charAt(i) == ',') {
                col++;
            }
            if (str.charAt(i) == ']') {
                break;
            }
        }
        col++;

        int[][] out = new int[row][col];

        str = str.replaceAll("\\[", "").replaceAll("\\]", "");

        String[] s1 = str.split(", ");

        int j = -1;
        for (int i = 0; i < s1.length; i++) {
            if (i % col == 0) {
                j++;
            }
            out[j][i % col] = Integer.parseInt(s1[i]);
        }
        return out;
    }

}
