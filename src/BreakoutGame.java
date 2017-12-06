import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import gameGUI.*;

public class BreakoutGame extends Application {
    @Override // Start method in Application class
    public void start(Stage primaryStage) throws Exception {
        SplashSingleton splashScene = SplashSingleton.getInstance();
        GameplaySingleton gameScene = GameplaySingleton.getInstance();
        gameScene.setStage(primaryStage);
        ConfirmationScene aboutScene = new ConfirmationScene(
            "\tThis game is based off the game \"Breakout\" published by Atari, Inc. " +
            "The goal is to destroy all the bricks on the field, by returning the ball with the paddle, " +
            "which is controlled by the mouse. " +
            "There are two types of bricks: power bricks and tough bricks. " +
            "Tough bricks have toughness, changing the number of times they need to be hit to destroy. " +
            "Power bricks have one toughness, and grant a power up comprised of a single letter when destroyed\n\n\n" +
            "\tPower bricks have a gold border, and have different colors inside to distinguish power ups:\n\n" +
            "- WHITE: 'C'lears a row\n" +
            "- RED: 'D'amages all bricks by one\n" +
            "- GREEN: 'L'ife is gained\n" +
            "- YELLOW: 'P'ower is doubled (damage)\n" +
            "- LIGHT BLUE: 'R'acket width is increased\n" +
            "- BLUE: 'S'peed of ball is reduced",
        false);
        aboutScene.getText().setFont(Font.font("sans-serif", 20));

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        primaryStage.setTitle("Breakout! Breakout!");
        primaryStage.setScene(splashScene);

        splashScene.setButtonOnAction(0, e -> {
            gameScene.setNewGame();
            primaryStage.setScene(gameScene);
            gameScene.go();
        });
        splashScene.setButtonOnAction(1, e -> {
            gameScene.load("player.dat");
            primaryStage.setScene(gameScene);
            gameScene.go();
        });
        splashScene.setButtonOnAction(2, e -> {
            primaryStage.setScene(aboutScene);
            aboutScene.setOnBack(f -> primaryStage.setScene(splashScene));
        });
        splashScene.setButtonOnAction(3, e -> primaryStage.close());

        gameScene.setButtonOnAction(0, e -> {
            gameScene.stop();
            primaryStage.setScene(aboutScene);
            aboutScene.setOnBack(f -> {
                primaryStage.setScene(gameScene);
                gameScene.go();
            });
        });
        gameScene.setButtonOnAction(2, e -> {
            gameScene.save("player.dat");
            gameScene.stop();
            primaryStage.setScene(splashScene);
        });

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
