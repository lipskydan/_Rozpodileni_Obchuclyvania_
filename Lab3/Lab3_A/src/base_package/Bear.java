package base_package;

import javafx.application.Platform;

public class Bear extends Thread {
    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       synchronized (Program.slider) {
            Platform.runLater(() -> Program.slider.setValue(0));
        }

    }
}
