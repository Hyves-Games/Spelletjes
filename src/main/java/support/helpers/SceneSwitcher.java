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
    private SceneEnum scene;
    private final Stage stage;
    private static SceneSwitcher instance;

    public SceneSwitcher(Stage stage) {
        this.stage = stage;
        this.stage.setWidth(900);
        this.stage.setHeight(600);

        SceneSwitcher.instance = this;
    }

    public static SceneSwitcher getInstance() {
        if (SceneSwitcher.instance == null) {
            throw new RuntimeException("Scene switcher has not been init in main");
        }

        return SceneSwitcher.instance;
    }

    public void change(SceneEnum sceneEnum) {
        ClientScene clientScene = sceneEnum.getClientScene();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Application.class.getResource(clientScene.path())
            );

            Scene scene = new Scene(fxmlLoader.load(), this.stage.getWidth(), this.stage.getHeight());

            this.stage.setTitle(clientScene.title());
            this.stage.setScene(scene);
            this.stage.show();

            this.scene = sceneEnum;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stage getStage() {
        return this.stage;
    }

    public SceneEnum getScene() {
        return this.scene;
    }
}
