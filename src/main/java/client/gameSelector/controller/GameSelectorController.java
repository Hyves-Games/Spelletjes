package client.gameSelector.controller;

import domain.game.model.TicTacToe;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import support.abstracts.AbstractGame;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

import java.util.HashMap;
import java.util.Map;

public class GameSelectorController {

    @FXML HBox container;

    public void initialize() {
        for (GameModeEnum gameModeValue : GameModeEnum.values()) {
            HashMap<String, String> gameModeInfo = getGameModeInfo(gameModeValue.toString());

            System.out.println(gameModeInfo.get("name"));

            VBox gameContainer = new VBox();
            Button gameButton = new Button();
            Label gameName = new Label(gameModeInfo.get("name"));

            Image icon = new Image(gameModeInfo.get("iconPath"));
            ImageView view = new ImageView(icon);

            view.setFitHeight(60);
            view.setFitWidth(60);

            gameButton.setGraphic(view);
            gameContainer.setSpacing(10);

            gameContainer.getChildren().add(gameButton);
            gameContainer.getChildren().add(gameName);
            container.getChildren().add(gameContainer);
        }
    }

    public HashMap<String, String> getGameModeInfo(String GameMode) {
        HashMap<String, String> gameInfo = new HashMap<String, String>();

        switch (GameMode) {
            case "TIC_TAC_TOE":

                gameInfo.put("name", TicTacToe.name);
                gameInfo.put("iconPath", TicTacToe.iconPath);

                break;
            default:
                return null;
        }

        return gameInfo;
    }

    public void onBackClick() {
        SceneSwitcher.getInstance().switchByEnum(SceneEnum.LOBBY);
    }
}
