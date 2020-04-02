package base_package;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.w3c.dom.html.HTMLButtonElement;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Main extends Application implements EventHandler<Event> {
    public static void main(String[] args) {
        launch(args);
    }

    static final int WINDOW_WIDTH = 1350; // 1400
    static final int WINDOW_HEIGHT = 650; // 700

    private Image bulletImage;
    private Image boomImage;
    private Image duckFlyImage;
    private Image duckFallImage;
    private Image hunterImage;
    private Image niceImage;

    public static Group item_group;
    public static volatile ArrayList<BulletThread> bullets;
    public static volatile ArrayList<DuckThread> ducks;
    public static BulletHandler bulletHandler;
    public static DuckHandler duckHandler;

    private ImageView hunterView;

    @Override
    public void start(Stage stage) {

        File fileBullet = new File("bullet.jpg");
        File fileBoom = new File("boom.jpg");
        File fileDuckFly = new File("duckFly.jpg");
        File fileDuckFall = new File("duckFall.jpg");
        File fileHunter = new File("hunter.jpg");
        File fileNice = new File("nice.jpg");

        try {
            bulletImage = new Image(fileBullet.toURI().toURL().toString());
            boomImage = new Image(fileBoom.toURI().toURL().toString());
            duckFlyImage = new Image(fileDuckFly.toURI().toURL().toString());
            duckFallImage = new Image(fileDuckFall.toURI().toURL().toString());
            hunterImage = new Image(fileHunter.toURI().toURL().toString());
            niceImage = new Image(fileNice.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        item_group = new Group();
        Scene scene = new Scene(item_group, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnMousePressed(this);
        stage.setScene(scene);
        stage.show();

        bullets = new ArrayList<>(100);
        ducks = new ArrayList<>(100);

        bulletHandler = new BulletHandler();
        bulletHandler.setDaemon(true);
        bulletHandler.start();

        duckHandler = new DuckHandler(duckFlyImage, duckFallImage, niceImage);
        duckHandler.setDaemon(true);
        duckHandler.start();

        makeHunter();
    }
    @Override
    public void handle(Event event) {
        if (event instanceof MouseEvent) {
            makeBullet((int)((MouseEvent) event).getX(), (int)((MouseEvent) event).getY());
        }
    }

    private void makeHunter() {
        hunterView = new ImageView(hunterImage);
        hunterView.setX(WINDOW_WIDTH/2 - 120);
        hunterView.setY(WINDOW_HEIGHT - hunterImage.getHeight());
        item_group.getChildren().add(hunterView);
    }
    private void makeBullet(int x, int y) {
        BulletThread bulletThread = new BulletThread(bulletImage, boomImage, (int) hunterView.getX(), (int)(WINDOW_HEIGHT-bulletImage.getHeight()), x, y);
        item_group.getChildren().add(bulletThread.getNode());
        bulletThread.setDaemon(true);
        bullets.add(bulletThread);
        bulletThread.start();
    }
}