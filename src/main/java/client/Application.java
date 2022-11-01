package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import support.actions.ConnectServerAction;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.AudioPlayer;
import support.helpers.ServerResponse;
import support.services.Server;

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

        AudioPlayer.play();
        AudioPlayer.setVolume(0.1);
    }

    public static void main(String[] args) {
        try {
            new ConnectServerAction("localhost", 7789);
            new Thread(() -> {
                Server server = Server.getConnection();

                while(server.isConnected()) {
                    ServerResponse response = server.read();
                    switch (response.getType()) {
//                        case MATCH ->
                    }
                }
            }).start();
        } catch (ServerConnectionFailedException e) {
            System.out.println("Connection failed");
        }

        launch();
    }
}