package base_package;

import java.util.Random;

public class Soldier {

    boolean left;

    Soldier() {
        Random random = new Random();
        this.left = random.nextBoolean();
    }
    public boolean isLeft() {
        return left;
    }
    public void turnLeft() {
        left = true;
    }
    public void turnRight() {
        left = false;
    }
    @Override
    public String toString() {
        return (left ? "<" : ">");
    }
}
