package client;

import javafx.stage.Stage;
import support.actions.ConnectServerAction;
import support.enums.SceneEnum;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.AudioPlayer;
import support.helpers.SceneSwitcher;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        new SceneSwitcher(stage).change(SceneEnum.LOGIN);

        AudioPlayer.play();
        AudioPlayer.setVolume(0);
    }

    public static void main(String[] args) {
        try {
            new ConnectServerAction("localhost", 7789);
        } catch (ServerConnectionFailedException ignored) {}

        launch();
    }
}