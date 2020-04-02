package base_package;

import java.util.ArrayList;
import java.util.Random;

public class Matrix {
    private ArrayList<ArrayList<Integer>> values;
    private ArrayList<ArrayList<MyReadWriteLock>> lock;

    public Matrix(int n) {
        lock = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            ArrayList<MyReadWriteLock> tmp = new ArrayList<>(n);
            for (int j = 0; j < n; j++)
                tmp.add(new MyReadWriteLock());
            lock.add(tmp);
        }

        Random random = new Random();
        values = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> tmp = new ArrayList<>(n);
            for (int j = 0; j < n; j++)
                tmp.add(Math.abs(random.nextInt()) % 100);
            values.add(tmp);
        }
    }

    public int getN() {
        return values.size();
    }

    public int getValue(int i, int j) throws Exception {
        if (i < 0 || j < 0)
            throw new Exception();
        return values.get(i).get(j);
    }

    public int setValue(int i, int j, int value) throws Exception {
        if (i < 0 || j < 0)
            throw new Exception();
        return values.get(i).set(j, value);
    }

    public void lockRead(int i, int j) {
        lock.get(i).get(j).readLock();
    }
    public void unlockRead(int i, int j) {
        lock.get(i).get(j).readUnlock();
    }
    public void lockWrite(int i, int j) {
        lock.get(i).get(j).writeLock();
    }
    public void unlockWrite(int i, int j) {
        lock.get(i).get(j).writeUnlock();
    }

    public void lockRead() {
        for (int i = 0; i < lock.size(); i++)
            for (int j = 0; j< lock.get(0).size(); j++)
                lockRead(i ,j);
    }

    public void unlockRead() {
        for (int i = 0; i < lock.size(); i++)
            for (int j = 0; j< lock.get(0).size(); j++)
                unlockRead(i ,j);
    }

    public void printMatrix() {
        try {
            for (int i = 0; i < getN(); i++){
                for (int j = 0; j < getN(); j++) {
                    System.out.print(getValue(i, j) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
