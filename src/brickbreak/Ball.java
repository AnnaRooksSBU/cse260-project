package brickbreak;

import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Ball extends Circle {
    private Rectangle hitbox;
    private int fieldWidth;
    private int power;
    private double dx;
    private double dy;
    private double baseY;
    private double baseV;
    private double speed;

    public Ball(int radius, int field, int yPos, Color color, int baseSpeed) {
        super(radius, color);
        hitbox = new Rectangle(radius*2,radius*2, Color.SNOW); // Color is set for debug purposes.
        hitbox.xProperty().bind(centerXProperty().subtract(getRadius()));
        hitbox.yProperty().bind(centerYProperty().subtract(getRadius()));
        fieldWidth = field; baseY = yPos; baseV = baseSpeed;
        reset();
    }
    public Rectangle getHitBox() { return hitbox; }

    public void run() {
        setCenterX(getCenterX() + dx);
        setCenterY(getCenterY() + dy); // To mimic cartesian
    }
    public void increaseSpeed(double increment) {
        double ratio = increment/speed +1;
        dx *= ratio; dy *= ratio;
        speed += increment;
    }

    public int getPower() { return power; }
    public double[] getSides() {
        return new double[] { // up, down, left, right
            getCenterY()-getRadius(), // up
            getCenterY()+getRadius(), // down
            getCenterX()-getRadius(), // left
            getCenterX()+getRadius()  // right
        };
    }

    public void doublePower() { power *= 2; }
    public void uCollide() { dy = -Math.abs(dy); }
    public void dCollide() { dy =  Math.abs(dy); }
    public void lCollide() { dx = -Math.abs(dx); }
    public void rCollide() { dx =  Math.abs(dx); }
    public void aCollide(double angle) {
        dx = speed*Math.cos(angle);
        dy = speed*Math.sin(angle);
    }

    public void reset() {
        setCenterX(fieldWidth/2);
        setCenterY(baseY);
        power = 1;
        speed = baseV;
        dx =  speed*Math.sqrt(0.5);
        dy = -speed*Math.sqrt(0.5);
    }
}
