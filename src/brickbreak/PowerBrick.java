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
            case 'C': return Color.SNOW;       // Clear row
            case 'D': return Color.RED;        // Damage all
            case 'F': return Color.BLACK;      // FF7: Super Nova
            case 'L': return Color.GREEN;      // Life gain
            case 'P': return Color.YELLOW;     // Power double
            case 'R': return Color.DODGERBLUE; // Racket width
            case 'S': return Color.BLUE;       // Speed reduction
        }
        return Color.FUCHSIA; // Default error color
    }
}
