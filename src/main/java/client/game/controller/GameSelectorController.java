package client.game.controller;

import domain.game.model.Reversi;
import domain.game.model.TicTacToe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import support.abstracts.AbstractGameBoard;
import support.enums.GameEnum;
import support.enums.SceneEnum;
import support.helpers.Auth;

public class GameSelectorController {
    @FXML HBox container;

    public void initialize() {
        for (GameEnum type : GameEnum.values()) {
            AbstractGameBoard gameProperties = this.getGameBoard(type);

            VBox gameContainer = new VBox();
            Button gameButton = new Button();
            Label gameName = new Label(gameProperties.getName());
            ImageView icon = this.createImage(gameProperties.getIconPath(), 60, 60);

            gameButton.setGraphic(icon);
            gameContainer.setSpacing(10);
            gameButton.setId(type.toString());
            gameButton.setOnAction(this::onGameChoose);

            gameContainer.getChildren().add(gameButton);
            gameContainer.getChildren().add(gameName);

            container.getChildren().add(gameContainer);
            container.setSpacing(10);
        }
    }

    public void onGameChoose(ActionEvent event) {
        Auth.setLastGame(GameEnum.valueOf(
                ((Node)event.getSource()).getId()
        ));

        SceneEnum.GAME_MODE_SELECTOR.switchTo();
    }

    public void onBackClick() {
        SceneEnum.LOBBY.switchTo();
    }

    private AbstractGameBoard getGameBoard(GameEnum type) {
        return switch (type) {
            case TIC_TAC_TOE -> new TicTacToe();
            case REVERSI -> new Reversi();
        };
    }

    private ImageView createImage(String name, double height, double width) {
        Image icon = new Image(name);
        ImageView image = new ImageView(icon);

        image.setFitHeight(height);
        image.setFitWidth(width);

        return image;
    }
}
