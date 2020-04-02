package base_package;

import javafx.application.Platform;

public class BulletHandler extends Thread {
    private static final int DELAY = 100;
    private static final int MIN_DISTANCE = 100;

    BulletHandler() {
        super();
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

            for (int i = 0; i < Main.bullets.size(); i++) {
                BulletThread bullet = Main.bullets.get(i);
                synchronized (bullet) {
                    if (bullet.isDead() || bullet.isOutOfRange()) {
                        Main.bullets.remove(bullet);
                        Platform.runLater(() -> Main.item_group.getChildren().remove(bullet.getNode()));
                        i--;
                    } else {
                        for (int j = 0; j < Main.ducks.size(); j++) {
                            DuckThread duck = Main.ducks.get(j);
                            synchronized (duck) {
                                double distance = Math.sqrt(
                                        (duck.getX() - bullet.getX())*(duck.getX() - bullet.getX()) +
                                                (duck.getY() - bullet.getY())*(duck.getY() - bullet.getY())
                                );
                                if (distance <= MIN_DISTANCE && duck.isOkay()) {
                                    bullet.MakeBoom();
                                    duck.MakeFall();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}