package base_package;


import javafx.application.Platform;

import java.util.concurrent.Semaphore;

class MyTread extends Thread {

    private int start_x;
    private int end_x;
    private int size;

    private static volatile boolean done = false;
    private static volatile Semaphore semaphore = new Semaphore(1);

    public MyTread(int start_x, int end_x) {
        getTask(start_x, end_x);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (done) { semaphore.release(); return; }
                action();
                semaphore.acquire();
                start_x += size*Main.COUNT_OF_THREADS;
                end_x = Math.min(Main.GRID_WIDTH, end_x+size*Main.COUNT_OF_THREADS);
                getTask(start_x, end_x);
                semaphore.release();
                if (start_x == end_x) return;
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(1);
            }
        }
    }

    private void getTask(int start_x, int end_x) {
        this.start_x = start_x;
        this.end_x = end_x;
        this.size = end_x - start_x;
    }

    private void action() {
        for (int i = start_x; i < end_x; i++) {
            for (int j = 0; j < Main.GRID_HEIGHT; j++) {
                if (i == Main.winni_x && j == Main.winni_y) {
                    try {
                        semaphore.acquire();
                        done = true;
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(2);
                    }
                    return;
                } else {
                    synchronized (Main.field.get(i).get(j)) {
                        int finalI = i;
                        int finalJ = j;
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                Main.field.get(finalI).get(finalJ).setStyle("-fx-background-color: green");
                            }
                        });
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
