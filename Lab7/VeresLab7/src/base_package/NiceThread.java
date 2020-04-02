package base_package;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NiceThread extends Thread {
    private static final int DELAY = 20;
    private static final int DISCRETE_STEP = 5;
    private ImageView imageView;
    private int up_height;
    private boolean up;

    NiceThread(Image niceImage, int pos_x) {
        super();
        this.imageView = new ImageView(niceImage);
        this.imageView.setX(pos_x);
        this.imageView.setY(Main.WINDOW_HEIGHT);
        Platform.runLater(() -> Main.item_group.getChildren().add(imageView));
        this.up_height = (int) niceImage.getHeight();
        this.up = true;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            if (interrupted()) return;
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (interrupted()) return;

            if (up) {
                if (imageView.getY() > Main.WINDOW_HEIGHT - this.up_height)
                    Platform.runLater(() -> imageView.setY(imageView.getY() - DISCRETE_STEP));
                else {
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    up = false;
                }
            } else {
                if (imageView.getY() < Main.WINDOW_HEIGHT)
                    Platform.runLater(() -> imageView.setY(imageView.getY() + DISCRETE_STEP));
                else return;
            }
        }
    }
}
