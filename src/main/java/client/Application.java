package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import support.actions.ConnectServerAction;
import support.actions.LoginServerAction;
import support.exceptions.NoServerConnectionException;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.Auth;
import support.helpers.Mediaplayer;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Application.class.getResource("menu/menu.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        stage.setScene(scene);

        stage.setTitle("Login");
        stage.show();

        Mediaplayer.play();
        Mediaplayer.setVolume(0.1);
    }

    public static void main(String[] args) {
        try {
            new ConnectServerAction("localhost", 7789);
        } catch (ServerConnectionFailedException e) {
            System.out.println("Connection failed");
        }

        launch();
    }
}