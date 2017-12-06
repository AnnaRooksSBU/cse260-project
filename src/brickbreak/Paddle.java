package brickbreak;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle {
    private int fieldWidth;
    private int baseWidth;
    private int baseY;
    public Paddle(int wide, int high, int field, int yPos, Color color) {
        super(wide, high, color);
        fieldWidth = field;
        baseWidth = wide;
        baseY = yPos;
        reset();
    }

    public void reset() {
        setX((fieldWidth-baseWidth)/2); setY(baseY);
        setWidth(baseWidth);
    }
    public void xFromScene(double x) {
        setX(Math.max(0,
             Math.min(  x-getWidth()/2, fieldWidth-getWidth()))); // Centered, but within window
    }
    public void collide(Ball ball) {
        if (localToScene(getBoundsInLocal()).intersects(
                ball.getHitBox().localToScene(ball.getHitBox().getBoundsInLocal()))) {
            double ratio = (ball.getCenterX()-getX())/getWidth();
            ratio = Math.min(Math.max(0.1,ratio),0.9); // 18 degrees mininum angle
            ball.aCollide(Math.PI*(1+ratio));
        }
    }
}
