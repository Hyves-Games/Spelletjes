package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import support.actions.ConnectServerAction;
import support.enums.SceneEnum;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.AudioPlayer;
import support.helpers.SceneSwitcher;
import support.helpers.ServerResponse;
import support.services.Server;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        SceneSwitcher switcher = new SceneSwitcher(stage);

        switcher.switchByEnum(SceneEnum.LOGIN);

        AudioPlayer.play();
        AudioPlayer.setVolume(0);

        System.out.print(SceneSwitcher.getInstance());
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
                        case OK, ERROR: server.responseHandled();
                    }
                }
            }).start();
        } catch (ServerConnectionFailedException e) {
            System.out.println("Connection failed");
        }

        launch();
    }
}