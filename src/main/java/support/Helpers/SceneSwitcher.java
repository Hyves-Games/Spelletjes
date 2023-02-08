package Support.Helpers;

import client.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import Support.Enums.SceneEnum;
import Support.Records.ClientScene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneSwitcher {
    private SceneEnum scene;
    private final Stage stage;
    private final Map<String, Dialog<?>> dialogs = new HashMap<>();

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

    public void addDialog(String key, Dialog<?> dialog) {
        this.dialogs.put(key, dialog);
    }

    public void removeDialog(String key) {
        Dialog<?> dialog = this.dialogs.get(key);

        if (dialog != null) {
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            dialog.close();

            this.dialogs.remove(key);
        }
    }
}
