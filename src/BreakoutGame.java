import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Scanner;

import gameGUI.*;

public class BreakoutGame extends Application {
    @Override // Start method in Application class
    public void start(Stage primaryStage) throws Exception {

        String aboutText;
        // "Stupid Scanner Tricks"
        // https://community.oracle.com/blogs/pat/2004/10/23/stupid-scanner-tricks
        try (Scanner sc = new Scanner(getClass().getResourceAsStream("resources/aboutText.txt"),"UTF-8")) {
            aboutText = sc.useDelimiter("\\A").hasNext() ? sc.next() : "";
        }

        // Scene creation
        SplashSingleton splashScene = SplashSingleton.getInstance();
        GameplaySingleton gameScene = GameplaySingleton.getInstance();
        ConfirmationScene aboutScene = new ConfirmationScene(aboutText,false);

        // Splash Screen Setup
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

        // Game Scene Setup
        gameScene.setStage(primaryStage);
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

        // About Scene Setup
        aboutScene.getText().setFont(Font.font("sans-serif", 20));

        // Stage setup
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/icon.png")));
        primaryStage.setTitle("Breakout! Breakout!");
        primaryStage.setScene(splashScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
