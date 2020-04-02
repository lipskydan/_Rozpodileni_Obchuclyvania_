package base_package;

import javafx.application.Platform;
import javafx.scene.image.Image;

import java.util.Random;

public class DuckHandler extends Thread {
    private static final int DELAY = 3000;
    private static final int MAX_DUCK_COUNT = 5;
    private Image duckFlyImage;
    private Image duckFallImage;
    private Image niceImage;

    DuckHandler(Image duckFlyImage, Image duckFallImage, Image niceImage) {
        super();
        this.duckFlyImage = duckFlyImage;
        this.duckFallImage = duckFallImage;
        this.niceImage = niceImage;
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

            if (Main.ducks.size() < MAX_DUCK_COUNT) {
                Random random = new Random();
                DuckThread duck = new DuckThread(duckFlyImage, duckFallImage, random.nextBoolean(), random.nextBoolean());
                Platform.runLater(() -> Main.item_group.getChildren().add(duck.getNode()));
                duck.setDaemon(true);
                Main.ducks.add(duck);
                duck.start();
            }

            for (int i = 0; i < Main.ducks.size(); i++) {
                DuckThread duck = Main.ducks.get(i);
                synchronized (duck) {
                    if (duck.getY() >= Main.WINDOW_HEIGHT - 50) {
                        NiceThread nice = new NiceThread(niceImage, (int) duck.getX());
                        nice.setDaemon(true);
                        nice.start();
                        Main.ducks.remove(duck);
                        i--;
                        Platform.runLater(() -> Main.item_group.getChildren().remove(duck));
                    }
                }
            }

        }
    }
}