package brickbreak;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

public class PowerBrick extends Brick {
    private char power;
    public PowerBrick(int width, int height, char power) {
        super(width, height, 1);
        this.power = power;
        getBrick().setFill(getColor());
        getBrick().setStroke(Color.GOLD);
        getBrick().setStrokeType(StrokeType.INSIDE);
        getBrick().setStrokeWidth(2*PADDING);
    }
    public char toChar() { return power; }

    // Color assignment helper method
    private Color getColor() {
        switch (power) {
            case 'c': return Color.SNOW;       // Clear row
            case 'd': return Color.RED;        // Damage all
            case 'f': return Color.BLACK;      // FF7: Super Nova
            case 'l': return Color.GREEN;      // Life gain
            case 'p': return Color.YELLOW;     // Power double
            case 'r': return Color.DODGERBLUE; // Racket width
            case 's': return Color.BLUE;       // Speed reduction
        }
        return Color.FUCHSIA; // Default error color
    }
}
