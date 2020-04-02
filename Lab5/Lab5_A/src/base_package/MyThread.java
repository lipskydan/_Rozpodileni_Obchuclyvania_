package base_package;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyThread extends Thread {
    private CyclicBarrier barrier;
    private ArrayList<Soldier> soldiers;
    private int from;
    private int to;

    private AtomicBoolean finished;
    private ArrayList<Boolean> threadFinished;
    private int index;

    MyThread(AtomicBoolean finished, ArrayList<Boolean> threadFinished, int index, CyclicBarrier barrier, ArrayList<Soldier> soldiers, int from, int to) {
        super();

        this.finished = finished;
        this.threadFinished = threadFinished;
        this.index = index;

        this.barrier = barrier;
        this.soldiers = soldiers;
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        super.run();
        while (!isOK() || !finished.get()) {
            if (isOK()) threadFinished.set(index, true);
            else threadFinished.set(index, false);
            for (int i = from; i < to; i++) {
                if (i == 0) {
                    if (!soldiers.get(i).isLeft() && soldiers.get(i+1).isLeft()) {
                        soldiers.get(i).turnLeft();
                    }
                } else if (i == soldiers.size()-1){
                    if (soldiers.get(i).isLeft() && !soldiers.get(i-1).isLeft()) {
                        soldiers.get(i).turnRight();
                    }
                } else {
                    if (soldiers.get(i).isLeft() && !soldiers.get(i-1).isLeft()) {
                        soldiers.get(i).turnRight();
                    }
                    else if (!soldiers.get(i).isLeft() && soldiers.get(i+1).isLeft()) {
                        soldiers.get(i).turnLeft();
                    }
                }
            }
            try {
                sleep(100);
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isOK() {
        for (int i = from; i < to; i++) {
            if (i == 0) {
                if (!soldiers.get(i).isLeft() && soldiers.get(i+1).isLeft())
                    return false;
            }
            else if (i == soldiers.size()-1) {
                if (soldiers.get(i).isLeft() && !soldiers.get(i-1).isLeft())
                    return false;
            } else {
                if (soldiers.get(i).isLeft() && !soldiers.get(i-1).isLeft())
                    return false;
                if (!soldiers.get(i).isLeft() && soldiers.get(i+1).isLeft())
                    return false;
            }
        }
        return true;
    }
}
