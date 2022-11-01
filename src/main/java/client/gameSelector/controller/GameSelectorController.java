package client.gameSelector.controller;

import domain.game.model.TicTacToe;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;
import java.util.HashMap;

public class GameSelectorController {

    @FXML HBox container;

    public void initialize() {
        for (GameModeEnum gameModeValue : GameModeEnum.values()) {
            HashMap<String, String> gameProperties = getGameModeInfo(gameModeValue.toString());

            VBox gameContainer = new VBox();
            Button gameButton = new Button();
            Label gameName = new Label(gameProperties.get("name"));

            ImageView icon = createImage(gameProperties.get("iconPath"), 60, 60);

            gameButton.setGraphic(icon);
            gameContainer.setSpacing(10);

            gameContainer.getChildren().add(gameButton);
            gameContainer.getChildren().add(gameName);
            container.getChildren().add(gameContainer);
        }
    }

    public HashMap<String, String> getGameModeInfo(String GameMode) {
        HashMap<String, String> gameProperties = new HashMap<String, String>();

        switch (GameMode) {
            case "TIC_TAC_TOE":

                gameProperties.put("name", TicTacToe.name);
                gameProperties.put("iconPath", TicTacToe.iconPath);

                break;
            default:
                return null;
        }

        return gameProperties;
    }

    public void onBackClick() {
        SceneSwitcher.getInstance().switchByEnum(SceneEnum.LOBBY);
    }

    public ImageView createImage(String name, double height, double width) {
        Image icon = new Image(name);
        ImageView image = new ImageView(icon);

        image.setFitHeight(60);
        image.setFitWidth(60);

        return image;
    }
}
