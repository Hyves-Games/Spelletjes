package client.game.controller;

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
import support.abstracts.AbstractGameBoard;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;
import java.util.HashMap;

public class GameSelectorController {

    @FXML HBox container;

    public void initialize() {
        for (GameModeEnum type : GameModeEnum.values()) {
            AbstractGameBoard gameProperties = this.getGameBoard(type);

            VBox gameContainer = new VBox();
            Button gameButton = new Button();
            Label gameName = new Label(gameProperties.getName());

            ImageView icon = this.createImage(gameProperties.getIconPath(), 60, 60);

            gameButton.setGraphic(icon);
            gameButton.setOnAction(this::onGameChoose);
            gameButton.setId(gameProperties.getName());
            gameContainer.setSpacing(10);

            gameContainer.getChildren().add(gameButton);
            gameContainer.getChildren().add(gameName);
            container.getChildren().add(gameContainer);
        }
    }

    public void onGameChoose(ActionEvent event) {
        //Gamemode print
        System.out.println(((Node)event.getSource()).getId());

        SceneSwitcher.getInstance().change(SceneEnum.GAME_MODE_SELECTOR);
    }

    public void onBackClick() {
        SceneSwitcher.getInstance().change(SceneEnum.LOBBY);
    }

    private AbstractGameBoard getGameBoard(GameModeEnum type) {
        return switch (type) {
            case TIC_TAC_TOE -> new TicTacToe();
        };
    }

    private ImageView createImage(String name, double height, double width) {
        System.out.println(name);

        Image icon = new Image(name);
        ImageView image = new ImageView(icon);

        image.setFitHeight(height);
        image.setFitWidth(width);

        return image;
    }
}
