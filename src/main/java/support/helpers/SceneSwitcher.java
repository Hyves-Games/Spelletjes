package support.helpers;

import client.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static void switchScene(ActionEvent event, String sceneName, String sceneTitle) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(
                    Application.class.getResource(sceneName)
            );

            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.setTitle(sceneTitle);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
