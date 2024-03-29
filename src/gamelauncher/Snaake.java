package gamelauncher;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Snaake extends Circle {

    private final List<Circle> tails;
    private int length = 0;
    private Direction currentDirection;
    private static final int STEP = 8;

    public Snaake(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        tails = new ArrayList<>();
        currentDirection = Direction.UP;
    }

    public void step() {
        for (int i = length - 1; i >= 0; i--) {
            if (i == 0) {
                tails.get(i).setCenterX(getCenterX());
                tails.get(i).setCenterY(getCenterY());
            } else {
                tails.get(i).setCenterX(tails.get(i - 1).getCenterX());
                tails.get(i).setCenterY(tails.get(i - 1).getCenterY());
            }
        }
        if (currentDirection == Direction.UP) {
            setCenterY(getCenterY() - STEP);
        } else if (currentDirection == Direction.DOWN) {
            setCenterY(getCenterY() + STEP);
        } else if (currentDirection == Direction.LEFT) {
            setCenterX(getCenterX() - STEP);
        } else if (currentDirection == Direction.RIGHT) {
            setCenterX(getCenterX() + STEP);
        }
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    private Circle endTail() {
        if (length == 0) {
            return this;
        }
        return tails.get(length - 1);
    }

    public void eat(Circle food) {
        Circle tail = endTail();
        food.setCenterX(tail.getCenterX());
        food.setCenterY(tail.getCenterY());
       food.setFill(Color.color(random(), random(), random()));
        tails.add(length++, food);
    }

    public boolean eatSelf() {
        for (int i = 0; i < length; i++) {
            if (this.getCenterX() == tails.get(i).getCenterX() && this.getCenterY() == tails.get(i).getCenterY()) {
                return true;
            }
        }
        return false;
    }

    public int getlength() {
        return length;
    }
}
