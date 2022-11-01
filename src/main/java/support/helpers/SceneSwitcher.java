package support.helpers;

import client.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import support.enums.SceneEnum;

import java.io.IOException;
import java.util.HashMap;

public class SceneSwitcher {

    private final Stage stage;
    protected static SceneSwitcher instance;

    private final HashMap<SceneEnum, ClientScene> availableScenes = new HashMap<>();
    public SceneSwitcher(Stage stage) {
        this.fillAvailableScenes();
        this.stage = stage;

        SceneSwitcher.instance = this;
    }

    public static SceneSwitcher getInstance() {
        if (SceneSwitcher.instance == null) {
            throw new RuntimeException("Scene switcher is not inited in main");
        }

        return SceneSwitcher.instance;
    }
    public Stage getStage() {
        return this.stage;
    }

    public void switchScene(ClientScene clientScene) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Application.class.getResource(clientScene.getPath())
            );

            Scene scene = new Scene(fxmlLoader.load(), this.stage.getWidth(), this.stage.getHeight());

            this.stage.setTitle(clientScene.getTitle());
            this.stage.setScene(scene);
            this.stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchByEnum(SceneEnum scene) {
        ClientScene clientScene = this.availableScenes.get(scene);

        this.switchScene(clientScene);
    }

    private void fillAvailableScenes() {
        this.availableScenes.put(
                SceneEnum.LOBBY, new ClientScene("menu/menu.fxml", "Lobby")
        );
        this.availableScenes.put(
                SceneEnum.LOGIN, new ClientScene("menu/menu.fxml", "Login")
        );
        this.availableScenes.put(
                SceneEnum.SETTING, new ClientScene("settings/settings.fxml", "Settings")
        );
        this.availableScenes.put(
                SceneEnum.TIC_TAC_TOE, new ClientScene("game/game.fxml", "Tic-Tac-Toe")
        );
    }
}
