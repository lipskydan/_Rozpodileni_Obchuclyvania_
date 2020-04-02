package task_a;

import javafx.application.Platform;

public class MyThread extends Thread {
    boolean up;
    String name;
    private boolean shutdown = false;

    MyThread(boolean up, String name) {
        this.up = up;
        this.name = name;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            synchronized (Program.main_slider) {
                try {
                    Platform.runLater(() -> {
                        if (up) {
                            if (Program.main_slider.getValue() < Program.main_slider.getMax())
                                Program.main_slider.setValue(Program.main_slider.getValue() + 1);
                        } else {
                            if (Program.main_slider.getValue() > Program.main_slider.getMin())
                                Program.main_slider.setValue(Program.main_slider.getValue() - 1);
                        }
                    });
                    Thread.sleep(500);
                } catch (InterruptedException ignored) { }

            }
        }
        System.out.println("Thread " + name + " is interrupted");
    }

}