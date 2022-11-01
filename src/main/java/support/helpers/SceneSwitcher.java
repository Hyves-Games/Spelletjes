package support.helpers;

import client.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import support.enums.SceneEnum;

import java.io.IOException;
import java.util.HashMap;

public class SceneSwitcher {

    private final Stage stage;
    private static SceneSwitcher instance;

    private final HashMap<SceneEnum, ClientScene> availableScenes = new HashMap<>() {{
        put(SceneEnum.LOBBY, new ClientScene("menu", "Lobby"));
        put(SceneEnum.LOGIN, new ClientScene("menu", "Login"));
        put(SceneEnum.SETTING, new ClientScene("settings", "Settings"));
        put(SceneEnum.TIC_TAC_TOE, new ClientScene("game", "Tic-Tac-Toe"));
        put(SceneEnum.PLAYERLIST, new ClientScene("playerList", "Players list"));
        put(SceneEnum.GAMEMODESELECTOR, new ClientScene("gameModeSelector", "Gamemode"));
        put(SceneEnum.GAMESELECTOR, new ClientScene("gameSelector", "Game"));
        put(SceneEnum.WAITROOM, new ClientScene("waitingRoom", "Finding a game"));
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

            Scene scene = new Scene(fxmlLoader.load(), this.stage.getWidth(), this.stage.getHeight());

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
