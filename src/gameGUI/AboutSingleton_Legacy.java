package gameGUI;
import static gameGUI.Constants.*;

import gameGUI.asset.ArcButton;

import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;

public class AboutSingleton extends Scene {
    private static final AboutSingleton instance = new AboutSingleton();
    public static AboutSingleton getInstance() { return instance; }

    private ArcButton backButton = new ArcButton("Back", WINDOW_WIDE);
    private StackPane buttonPane;
    private StackPane root;
    private AboutSingleton() {
        super(new StackPane(), WINDOW_WIDE, WINDOW_HIGH);

        // Game info initialization


        // Button pane initialization
        buttonPane = new StackPane(backButton);
        buttonPane.setAlignment(Pos.BOTTOM_CENTER);
        buttonPane.setStyle("-fx-background-color: " + COLOR_MENU + ";");
        root = (StackPane) getRoot();
        root.getChildren().add(buttonPane);
    }

    public void setOnBack(EventHandler<ActionEvent> handler) {
        backButton.setOnAction(handler);
    }
}
