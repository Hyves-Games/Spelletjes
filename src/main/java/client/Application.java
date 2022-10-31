package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import support.helpers.Mediaplayer;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Application.class.getResource("menu/menu.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setTitle("Lobby");
        stage.setScene(scene);
        stage.show();

//        Mediaplayer.play();
    }

    public static void main(String[] args) {
        launch();
    }
}