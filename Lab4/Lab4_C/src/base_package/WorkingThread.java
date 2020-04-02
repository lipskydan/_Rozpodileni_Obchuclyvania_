package base_package;

import java.util.Random;

public class WorkingThread extends Thread {
    private Matrix matrix;
    private int task;

    public WorkingThread(Matrix matrix, int task) {
        this.matrix = matrix;
        this.task = task;
    }
    @Override
    public void run() {
        switch (task) {
            case 1:
                changePrice();
                break;
            case 2:
                addDeleteWay();
                break;
            case 3:
                showPrice();
                break;
            case 4:
                showMatrix();
                break;
            default:
                System.out.println("Unknown task!");
        }
    }

    public void changePrice() {
        Random random = new Random();
        while (true) {
            int i = Math.abs(random.nextInt()) % matrix.getN();
            int j = Math.abs(random.nextInt()) % matrix.getN();
            int new_price = Math.abs(random.nextInt()) % 100;

            try {
                while (matrix.getValue(i, j) == -1) {
                    i = Math.abs(random.nextInt()) % matrix.getN();
                    j = Math.abs(random.nextInt()) % matrix.getN();
                    new_price = Math.abs(random.nextInt()) % 100;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            matrix.lockWrite(i, j);
            System.out.println("[WorkingThread::changePrice()] Changing price: Start");
            try {
                matrix.setValue(i, j, new_price);
                System.out.println("[WorkingThread::changePrice()] Changing price: from " + i + " to " + j + " is " + new_price);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("[WorkingThread::changePrice()] Changing price: Finish");
            matrix.unlockWrite(i, j);

            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addDeleteWay() {
        Random random = new Random();
        while (true) {
            int i = Math.abs(random.nextInt()) % matrix.getN();
            int j = Math.abs(random.nextInt()) % matrix.getN();
            boolean add = random.nextBoolean();

            if (add) {
                try {
                    while (matrix.getValue(i, j) == -1) {
                        i = Math.abs(random.nextInt()) % matrix.getN();
                        j = Math.abs(random.nextInt()) % matrix.getN();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    while (matrix.getValue(i, j) != -1) {
                        i = Math.abs(random.nextInt()) % matrix.getN();
                        j = Math.abs(random.nextInt()) % matrix.getN();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            matrix.lockWrite(i, j);
            System.out.println("[WorkingThread::addDeleteWay()] Operation with way: Start");
            try {
                if (add) {
                    int new_price = Math.abs(random.nextInt()) % 100;
                    try {
                        matrix.setValue(i, j, new_price);
                        System.out.println("[WorkingThread::addDeleteWay()] Way from " + i + " to " + j + " was ADDED, price is " + new_price);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        matrix.setValue(i, j, -1);
                        System.out.println("[WorkingThread::addDeleteWay()] Way from " + i + " to " + j + "was DELETED");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                matrix.setValue(i, j, -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("[WorkingThread::addDeleteWay()] Operation with way: Finished");
            matrix.unlockWrite(i, j);

            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void showPrice() {
        Random random = new Random();
        while (true) {
            int i = Math.abs(random.nextInt()) % matrix.getN();
            int j = Math.abs(random.nextInt()) % matrix.getN();
            try {
                while (matrix.getValue(i, j) < 0)
                    j = Math.abs(random.nextInt()) % matrix.getN();
            } catch (Exception e) {
                e.printStackTrace();
            }

            matrix.lockRead(i, j);
            System.out.println("[WorkingThread::showPrice()]: Start");
            try {
                System.out.println("[WorkingThread::showPrice()] Price: from " + i + " to " + j + " costs = " + matrix.getValue(i, j));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("[WorkingThread::showPrice()]: End");
            matrix.unlockRead(i, j);

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void showMatrix() {
        while (true) {
            matrix.lockRead();
            System.out.println("[WorkingThread::showMatrix()]: Start");
            matrix.printMatrix();
            System.out.println("[WorkingThread::showMatrix()]: End");
            matrix.unlockRead();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}