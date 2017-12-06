package gameGUI;

import static gameGUI.Constants.*;
import brickbreak.*;
import gameGUI.asset.*;

import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class GameplaySingleton extends Scene {
    private static GameplaySingleton instance = new GameplaySingleton(5);
    public static GameplaySingleton getInstance() { return instance; }

    private int lives;
    private char power;
    private StackPane root;
    private Pane gamePane;
    private Level brickPane;
    private Ball ball;
    private Paddle paddle;
    private StackPane buttonPane;
    private HBox buttonHBox;
    private ArcButton[] buttons = { new ArcButton("About/Pause", WINDOW_WIDE*0.4),
                                    new ArcButton(" ", WINDOW_WIDE*0.2),
                                    new ArcButton("Save and Return", WINDOW_WIDE*0.4) };
    private Stage primaryStage;
    private ConfirmationScene loseScene;
    private ConfirmationScene winScene;

    private Timeline animate = new Timeline(new KeyFrame(Duration.millis(20), e -> run()));

    private GameplaySingleton(int lifes) {
        super(new StackPane(), WINDOW_WIDE, WINDOW_HIGH);
        root = (StackPane) getRoot();
        // Game level section initialization
        lives = lifes - 1; // Ball on screen
        power = ' ';
        // Bricks initialisation
        brickPane = new Level(BRICK_WIDE,BRICK_HIGH);
        brickPane.loadLevel(0);
        // Ball
        ball = new Ball(15, WINDOW_WIDE,WINDOW_HIGH-BUTTON_HIGH*5,
                        Color.web(COLOR_PLAY), 5);
        // Paddle
        paddle = new Paddle(100,15, WINDOW_WIDE,WINDOW_HIGH-BUTTON_HIGH*3,
                            Color.web(COLOR_PLAY));
        // Game's panel initialization
        gamePane = new Pane(brickPane, ball, paddle);


        // Buttons section initialization
        // Special case, center "button", stats
        updateStats();
        buttons[1].setPrefSize(WINDOW_WIDE*0.2,BUTTON_HIGH-10);
        buttons[1].setDisable(true);
        // Buttons Box initialization
        buttonHBox = new HBox(15, buttons[0], buttons[1], buttons[2]);
        buttonHBox.setMaxSize(WINDOW_WIDE, BUTTON_HIGH);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setStyle("-fx-background-color: " + COLOR_MENU + ";");
        // Buttons Pane initialization
        buttonPane = new StackPane(buttonHBox);
        buttonPane.setAlignment(Pos.BOTTOM_CENTER);


        // Final pane initialization
        root.getChildren().addAll(gamePane, buttonPane);
        root.setStyle("-fx-background-color: " + COLOR_BACK + ";");
        setOnMouseClicked(e -> execPower());
        setOnMouseMoved(e -> paddle.xFromScene(e.getSceneX()));
        animate.setCycleCount(Timeline.INDEFINITE);

        // Extra: Win/Lose scene setup
        loseScene = new ConfirmationScene("YOU LOSE", true);
        winScene = new ConfirmationScene("YOU BEAT GAME YAY", true);
        loseScene.getText().setFont(Font.font("sans-serif", FontWeight.BLACK, 60));
        winScene.getText().setFont(Font.font("sans-serif", FontWeight.BLACK, 60));
        loseScene.setOnBack(e -> {
            setNewGame();
            primaryStage.setScene(this);
            go(); });
        winScene.setOnBack(e -> {
            setNewGame();
            primaryStage.setScene(this);
            go();
        });
    }


    public void setButtonOnAction(int i, EventHandler<ActionEvent> handler) {
        buttons[i].setOnAction(handler);
    }
    public void go() { animate.play(); }
    public void stop() { animate.pause(); }
    public void save(String location) {
        try (ObjectOutputStream os
                = new ObjectOutputStream(
                  new FileOutputStream(location))) {
            os.writeObject(lives);
            os.writeObject(brickPane.getLevel());
            os.writeObject(brickPane.exportMap());
        } catch (IOException e) { System.out.println(e.getMessage()); }
    }
    public void load(String location) {
        try (ObjectInputStream is
                = new ObjectInputStream(
                  new FileInputStream(location))) {
            lives = (int) is.readObject();
            brickPane.setLevel((int) is.readObject());
            brickPane.importMap((char[][]) is.readObject());
        } catch (IOException e) { System.out.println(e.getMessage()); }
          catch (ClassNotFoundException e) { System.out.println(e.getMessage()); }
        resetGame();
    }

    private void resetGame() {
        power = ' ';
        updateStats();
        ball.reset();
        paddle.reset();
    }
    public void setNewGame() {
        brickPane.loadLevel(0);
        resetGame();
    }

    private void run() {
        ball.run();
        double[] side = ball.getSides();

        if (side[2] < 0) ball.rCollide();
        if (side[3] > WINDOW_WIDE) ball.lCollide();
        if (side[0] < 0) ball.dCollide();
        // Ball is out of field
        if (ball.getCenterY() > WINDOW_HIGH - BUTTON_HIGH) loseLife();
        // Collides with brick
        char newPower = brickPane.collide(ball);
        if (!(newPower == ' ' || newPower == power)) {
            power = newPower;
            updateStats();
        }
        // Collides with paddle
        paddle.collide(ball);

        // Level complete
        int levelState = brickPane.advanceLevel();
        if (levelState == 1) resetGame(); // Stuff doesn't carry over
        else if (levelState == -1) winGame();
    }

    private void loseLife() {
        lives--;
        resetGame();
        if (lives <= 0) loseGame();
        else {
            animate.pause();
            try { TimeUnit.SECONDS.sleep(1); }
            catch (InterruptedException e) { e.getMessage(); }
            animate.play();
        }
    }
    private void loseGame() {
        stop();
        primaryStage.setScene(loseScene);
    }
    private void winGame() {
        stop();
        primaryStage.setScene(winScene);
    }

    // This needs work to make more elegant, but I dont know how else
    // to do it at the moment
    public void setStage(Stage ps) { primaryStage = ps; }

    private void execPower() {
        switch (power) {
            case ' ': break; // No power
            case 'c': brickPane.clearRow();
                      break; // Clear row
            case 'd': brickPane.damageAll();
                      break; // Damage all
            case 'f': bosep();
                      break; // FF7: SN
            case 'l': lives++; updateStats();
                      break; // Life gain
            case 'p': ball.doublePower();
                      break; // Power doubles
            case 'r': paddle.setWidth(paddle.getWidth()+50);
                      break; // Racket width
        }
        // in case of unknown power, nothing happens and power is reset
        power = ' ';
        updateStats();
    }

    private void updateStats() {
        buttons[1].setText(
            "Lives: " +lives+
            "\nPower: " +power
        );
    }
    private void bosep() {}
}
