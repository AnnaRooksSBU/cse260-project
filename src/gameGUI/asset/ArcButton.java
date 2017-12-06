package gameGUI.asset;
import static gameGUI.Constants.*;

import javafx.scene.control.Button;

public class ArcButton extends Button {
    public ArcButton(String content, double width) {
        super(content);
        setStyle(btStyle(COLOR_BT_N));
        setOnMouseEntered(e -> setStyle(btStyle(COLOR_BT_H)));
        setOnMouseExited(e -> setStyle(btStyle(COLOR_BT_N)));
        setPrefSize(width-BUTTON_PADD, BUTTON_HIGH-BUTTON_PADD);
    }

    // Quick helper method for button styles
    private String btStyle(String col) {
       return "-fx-background-color: " +col+ ";"
            + "-fx-text-fill: " +COLOR_TEXT+ ";";
    }
}
