import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import gameGUI.*;

public class BreakoutGame extends Application {
    @Override // Start method in Application class
    public void start(Stage primaryStage) throws Exception {
        SplashSingleton splashScene = SplashSingleton.getInstance();
        GameplaySingleton gameScene = GameplaySingleton.getInstance();
        AboutSingleton aboutScene = AboutSingleton.getInstance();

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
