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

public class GameSelectorController {

    @FXML HBox container;

    public void initialize() {
        for (GameModeEnum gameModeValue : GameModeEnum.values()) {
            AbstractGame gameMode = getGameModeInfo(gameModeValue.toString());

            VBox gameContainer = new VBox();
            Button gameButton = new Button();
            Label gameName = new Label(gameMode.getName());

            Image img = new Image(gameMode.getIconPath());
            ImageView view = new ImageView(img);

            view.setFitHeight(60);
            view.setFitWidth(60);

            gameButton.setGraphic(view);
            gameContainer.setSpacing(10);

            gameContainer.getChildren().add(gameButton);
            gameContainer.getChildren().add(gameName);
            container.getChildren().add(gameContainer);
        }
    }

    public AbstractGame getGameModeInfo(String GameMode) {

        switch (GameMode) {
            case "TIC_TAC_TOE":
                return new TicTacToe();
            default:
                return null;
        }
    }

    public void onBackClick() {
        SceneSwitcher.getInstance().switchByEnum(SceneEnum.LOBBY);
    }
}
