package base_package;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewProgress extends Thread {
    private Line line;
    private ArrayList<Boolean> threadFinished;
    private AtomicBoolean finished;

    ViewProgress(Line line, AtomicBoolean finished, ArrayList<Boolean> threadFinished) {
        super();
        this.line = line;
        this.finished = finished;
        this.threadFinished = threadFinished;
    }

    @Override
    public void run() {
        super.run();
        line.printLineState();
        boolean the_end = threadFinished.get(0);
        for (Boolean b : threadFinished)
            the_end &= b;
        if (the_end)
            finished.set(true);
    }
}
