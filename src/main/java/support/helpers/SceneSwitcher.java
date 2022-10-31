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

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            double stageHeight = ((Node)event.getSource()).getScene().getHeight();
            double stageWidth = ((Node)event.getSource()).getScene().getWidth();

            Scene scene = new Scene(fxmlLoader.load(), stageWidth, stageHeight);

            stage.setTitle(sceneTitle);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
