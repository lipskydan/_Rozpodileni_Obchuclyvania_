package task_b;

import javafx.application.Platform;

public class MyThread extends Thread {
    private boolean up;
    private String name;
    private boolean shutdown = false;

    MyThread(boolean up, String name) {
        this.up = up;
        this.name = name;
    }

    @Override
    public void run() {
        if (Program.semafore.get() != 0) SetStateText("Critical section is used!");
        while (Program.semafore.get() != 0) {}
       if (Program.semafore.compareAndSet(0,1)){
           SetStateText("Critical section is just taken");
           System.out.println("Thread " + name + " run");
           while (!shutdown) {
               synchronized (Program.main_slider) {
                   if (!up) {
                       if (Program.main_slider.getValue() < Program.main_slider.getMax())
                           Program.main_slider.setValue(Program.main_slider.getValue() + 1);
                   } else {
                       if (Program.main_slider.getValue() > Program.main_slider.getMin())
                           Program.main_slider.setValue(Program.main_slider.getValue() - 1);
                   }
                   try {
                       Thread.sleep(50);
                   } catch (InterruptedException ignored) { }
               }
           }
           Program.semafore.set(0);
           SetStateText("Critical section is free");
           System.out.println("Thread " + name + " is interrupted");
       }

    }

    private void SetStateText(String text) {
        Platform.runLater(() -> Program.semafore_state.setText(text));
    }
    public void MyInterrupt() {
        shutdown = true;
    }
}