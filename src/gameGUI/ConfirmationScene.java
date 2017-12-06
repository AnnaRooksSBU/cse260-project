package gameGUI;
import static gameGUI.Constants.*;

import gameGUI.asset.ArcButton;

import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;

public class ConfirmationScene extends Scene {
    private ArcButton backButton = new ArcButton("Back", WINDOW_WIDE);
    private StackPane buttonColorPane;
    private StackPane buttonPane;
    private StackPane root;
    private Text text;
    public ConfirmationScene(String content, boolean centered) {
        super(new StackPane(), WINDOW_WIDE, WINDOW_HIGH);

        // Text initialization
        text = new Text(content);
        text.setFill(Color.web(COLOR_TEXT));
        text.setWrappingWidth(WINDOW_WIDE-30);
        text.setTextAlignment(centered ?
                TextAlignment.CENTER :
                TextAlignment.JUSTIFY);

        // Button pane initialization
        buttonColorPane = new StackPane(backButton);
        buttonColorPane.setMaxSize(WINDOW_WIDE, BUTTON_HIGH);
        buttonColorPane.setAlignment(Pos.CENTER);
        buttonColorPane.setStyle("-fx-background-color: " + COLOR_MENU + ";");
        // Container pane, since style and alignment seem to conflict?
        buttonPane = new StackPane(buttonColorPane);
        buttonPane.setAlignment(Pos.BOTTOM_CENTER);

        // Put the stuff in
        root = (StackPane) getRoot();
        root.setStyle("-fx-background-color: " + COLOR_BACK + ";");
        root.getChildren().addAll(buttonPane,text);
    }

    public Text getText() { return text; }

    public void setOnBack(EventHandler<ActionEvent> handler) {
        backButton.setOnAction(handler);
    }
}
