package base_package;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DuckThread extends Thread {
    private static final int DELAY = 20;
    private static final int DISCRETE_STEP = 2;

    private Image duckFlyImage;
    private Image duckFallImage;
    private ImageView imageView;

    private int delta_x;
    private int delta_y;

    private boolean fall;

    DuckThread(Image duckFlyImage, Image duckFallImage, boolean left, boolean up) {
        super();
        this.delta_x = DISCRETE_STEP;
        this.delta_y = DISCRETE_STEP;

        this.duckFlyImage = duckFlyImage;
        this.duckFallImage = duckFallImage;
        imageView = new ImageView(duckFlyImage);

        if (left)
            imageView.setX(0);
        else
            imageView.setX(Main.WINDOW_WIDTH - imageView.getFitWidth());

        if (up)
            imageView.setY(Main.WINDOW_HEIGHT/3);
        else
            imageView.setY(0);
        this.fall = false;
    }
    @Override
    public void run() {
        super.run();
        while (true) {
            if (interrupted()) return;
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                return;
            }
            if (interrupted()) return;

            if (fall) {
                if (imageView.getY() > Main.WINDOW_HEIGHT)
                    return;
            } else {
                if (imageView.getX() < 0 || imageView.getX() + imageView.getFitWidth() > Main.WINDOW_WIDTH) {
                    this.delta_x *= -1;
                    Platform.runLater(() -> imageView.setX(imageView.getX() + delta_x*2));
                }
                if (imageView.getY() + imageView.getFitHeight() < 0 || imageView.getY() > Main.WINDOW_HEIGHT/3) {
                    this.delta_y *= -1;
                    Platform.runLater(() -> imageView.setY(imageView.getY() + delta_y*2));
                }
            }

            Platform.runLater(() -> {
                imageView.setX(imageView.getX() + delta_x);
                imageView.setY(imageView.getY() + delta_y);
            });
        }
    }
    public ImageView getNode() {
        return imageView;
    }
    public double getX() {
        return imageView.getX();
    }
    public double getY() {
        return imageView.getY();
    }
    public void MakeFall() {
        if (!fall) {
            delta_x = 0;
            delta_y = Math.abs(delta_y)*2;
            Platform.runLater(() -> imageView.setImage(duckFallImage));
        }
        fall = true;
    }
    public Boolean isOkay(){
        if (fall) return false;
        return true;
    }
}