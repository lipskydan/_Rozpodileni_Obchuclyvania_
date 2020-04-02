package base_package;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Line {
    private CyclicBarrier barrier;
    private ArrayList<Soldier> soldiers;
    private ArrayList<MyThread> myThreads;

    private ArrayList<Boolean> threadFinished;
    private AtomicBoolean finished;

    Line(int soldier_count, int thread_count) {
        super();

        threadFinished = new ArrayList<>(thread_count);
        for (int i = 0; i < thread_count; i++) threadFinished.add(false);
        this.finished = new AtomicBoolean(false);

        this.barrier = new CyclicBarrier(thread_count, new ViewProgress(this, finished, threadFinished));

        this.soldiers = new ArrayList<>(soldier_count);
        for (int i = 0; i < soldier_count; i++)
            this.soldiers.add(new Soldier());
        this.myThreads = new ArrayList<>();
        for (int i = 0; i < thread_count; i++) {
            int interval = soldier_count/thread_count;
            MyThread thread = new MyThread(finished, threadFinished, i, barrier, soldiers, i*interval, (i+1)*interval);
            thread.setDaemon(true);
            myThreads.add(thread);
        }
    }

    public void printLineState() {
        StringBuilder res = new StringBuilder();
        int i = 0;
        for (Soldier soldier : soldiers) {
            if (i > soldiers.size()/ myThreads.size()) {
                res.append(" ");
                i = 0;
            }
            res.append(soldier);
            i++;
        }
        System.out.println(res);
    }

    public void start() {
        printLineState();
        for (MyThread thread : myThreads) {
            thread.start();
        }
        for (MyThread thread : myThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
