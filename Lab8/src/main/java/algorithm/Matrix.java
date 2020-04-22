package algorithm;


import java.util.Random;

public class Matrix {
    private final int[][] matrix;

    private final int size;

    Matrix(int size) {
        this.size = size;
        matrix = new int[size][];
        for (int i = 0; i < size; i++)
            matrix[i] = new int[size];
    }

    Matrix(int[] lineMatrix) {
        size = (int)Math.sqrt(lineMatrix.length);

        matrix = new int[size][];
        for (int i = 0; i < size; i++) {
            matrix[i] = new int[size];
            System.arraycopy(lineMatrix, i * size, matrix[i], 0, size);
        }
    }

    public void copyFromLine(int[] lineMatrix) {
        for (int i = 0; i < size; i++) {
            System.arraycopy(lineMatrix, i * size, matrix[i], 0, size);
        }
    }

    public void fillRandom(int maxNumber) {
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = rand.nextInt(maxNumber);
    }

    public int[] getMatrix() {
        int[] linedMatrix = new int[size * size];
        for (int i = 0; i < size; i++)
            System.arraycopy(matrix[i], 0, linedMatrix, i * size, size);
        return linedMatrix;
    }

    public int getSize() {
        return size;
    }

    public int get(int i, int j) {
        return matrix[i][j];
    }

    public void set(int i, int j, int value) {
        matrix[i][j] = value;
    }

    public void add(int i, int j, int value) {
        matrix[i][j] += value;
    }
}