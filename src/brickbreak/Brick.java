package brickbreak;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;

public class Brick extends StackPane {
    // List of the 9 reference colors of each toughness, and a 0 color
    public static Color[] colors = { Color.FUCHSIA,
        Color.RED,         Color.ORANGERED,   Color.ORANGE,
        Color.YELLOW,      Color.GREENYELLOW, Color.YELLOWGREEN,
        Color.TURQUOISE,   Color.BLUE,        Color.ORCHID
    };
    // Size is reduced slightly to make it look nicer
    protected static final int PADDING = 2;
    private Rectangle brick;
    private int toughness;
    private Color color;
    public Brick(int width, int height, int toughness) {
        super();
        brick = new Rectangle(width-2*PADDING, height-2*PADDING, colors[toughness]);
        this.toughness = toughness;
        getChildren().add(brick);
    }

    // Returns true if it died
    public void collide(Ball ball) {
        damage(ball.getPower());

        // distance between
        double dx = Math.abs(ball.getCenterX() - (brick.getX()+brick.getWidth()/2));
        double dy = Math.abs(ball.getCenterY() - (brick.getY()+brick.getHeight()/2));
        // range between
        double rx = ball.getRadius() + brick.getX()+brick.getWidth()/2;
        double ry = ball.getRadius() + brick.getY()+brick.getHeight()/2;

        // Collision
        if(ball.getCenterY() <= brick.getY())
          ball.uCollide();
        else if(ball.getCenterY() >= brick.getY() + brick.getHeight())
          ball.dCollide();
        else if(ball.getCenterX() <= brick.getX())
          ball.lCollide();
        else if(ball.getCenterX() >= brick.getX() + brick.getWidth())
            ball.rCollide();
    }

    public void damage(int p) { toughness -= p; updateColor(); }
    public boolean isDestroyed() { return toughness <= 0; }
    public void updateColor() { brick.setFill(colors[Math.max(0,toughness)]); }
    protected Rectangle getBrick() { return brick; }
    public char toChar() { return Character.forDigit(toughness,10); }
}
