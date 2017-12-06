package gameGUI;

import static gameGUI.Constants.*;
import brickbreak.*;
import gameGUI.asset.*;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SplashSingleton extends Scene {
    private static final SplashSingleton instance = new SplashSingleton();
    public static SplashSingleton getInstance() { return instance; }

    private StackPane root;
    private Level brickPane;
    private Paddle paddle;
    private Pane demoPane;
    private Text text = new Text("BREAKOUT");
    private GridPane buttonGridPane = new GridPane();
    private StackPane buttonPane;
    private ArcButton[] buttons = {
                                    new ArcButton("New Game", WINDOW_WIDE/2),
                                    new ArcButton("Load Game", WINDOW_WIDE/2),
                                    new ArcButton("About", WINDOW_WIDE/2),
                                    new ArcButton("Quit", WINDOW_WIDE/2)
                               };

    private SplashSingleton() {
        super(new StackPane(), WINDOW_WIDE, WINDOW_HIGH);
        root = (StackPane) getRoot();
        // Demo pane initialization
        brickPane = new Level(BRICK_WIDE, BRICK_HIGH);
        brickPane.loadLevel((int) (brickbreak.Level.LEVELS.length*Math.random()));
        brickPane.setAlignment(Pos.TOP_CENTER);
        paddle = new Paddle(100,15, WINDOW_WIDE, WINDOW_HIGH-BUTTON_HIGH*3,
                            Color.web(COLOR_PLAY));
        demoPane = new Pane (brickPane,paddle);

        // Text initialization
        text.setFill(Color.web(COLOR_TEXT));
        text.setFont(Font.font("sans-serif", FontWeight.BLACK, 60));

        // Buttons pane initialization (Worth making its own class?)
        buttonGridPane.setHgap(10); buttonGridPane.setVgap(10);
        buttonGridPane.setMaxSize(WINDOW_WIDE, BUTTON_HIGH*2);
        buttonGridPane.setAlignment(Pos.CENTER);
        buttonGridPane.setStyle("-fx-background-color: " + COLOR_MENU + ";");
        // Add the buttons in the spaces (coordinates are binary representation of index)
        buttonGridPane.add(buttons[0], 0,0);
        buttonGridPane.add(buttons[1], 1,0);
        buttonGridPane.add(buttons[2], 0,1);
        buttonGridPane.add(buttons[3], 1,1);

        // Buttons pane wrapping, as colors are confusing
        buttonPane = new StackPane(buttonGridPane);
        buttonPane.setAlignment(Pos.BOTTOM_CENTER);

        root.getChildren().addAll(demoPane, text, buttonPane);
        root.setStyle("-fx-background-color: " + COLOR_BACK + ";");
        setOnMouseMoved(e -> paddle.xFromScene(e.getSceneX()));
    }

    public void setButtonOnAction(int i, EventHandler<ActionEvent> handler) {
        buttons[i].setOnAction(handler);
    }
    public Paddle getPaddle() { return paddle; }
}
