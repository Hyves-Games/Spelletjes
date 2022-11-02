package support.helpers;

import client.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import support.enums.SceneEnum;
import support.records.ClientScene;

import java.io.IOException;
import java.util.HashMap;

public class SceneSwitcher {
    private final Stage stage;
    private static SceneSwitcher instance;

    private final HashMap<SceneEnum, ClientScene> availableScenes = new HashMap<>() {{
        put(SceneEnum.LOGIN, new ClientScene("authenticator", "login", "Login"));
        put(SceneEnum.LOBBY, new ClientScene("lobby", "lobby", "Lobby"));
        put(SceneEnum.SETTING, new ClientScene("settings", "settings", "Settings"));
        put(SceneEnum.TIC_TAC_TOE, new ClientScene("game/board", "ticTacToe", "Tic-Tac-Toe"));
        put(SceneEnum.PLAYER_LIST, new ClientScene("playerList", "playerList","Players list"));
        put(SceneEnum.GAME_MODE_SELECTOR, new ClientScene("game","gameModeSelector", "Game mode"));
        put(SceneEnum.GAME_SELECTOR, new ClientScene("game","gameSelector", "Game"));
        put(SceneEnum.WAIT_ROOM, new ClientScene("waitingRoom", "waitingRoom","Finding a game"));
    }};

    public SceneSwitcher(Stage stage) {
        this.stage = stage;
        this.stage.setWidth(1300);
        this.stage.setHeight(800);

        SceneSwitcher.instance = this;
    }

    public static SceneSwitcher getInstance() {
        if (SceneSwitcher.instance == null) {
            throw new RuntimeException("Scene switcher has not been init in main");
        }

        return SceneSwitcher.instance;
    }

    public void change(SceneEnum sceneEnum) {
        ClientScene clientScene = this.availableScenes.get(sceneEnum);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Application.class.getResource(clientScene.path())
            );

            Scene scene = new Scene(fxmlLoader.load(), this.stage.getWidth(), this.stage.getHeight() - 28);

            this.stage.setTitle(clientScene.title());
            this.stage.setScene(scene);
            this.stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stage getStage() {
        return this.stage;
    }
}
