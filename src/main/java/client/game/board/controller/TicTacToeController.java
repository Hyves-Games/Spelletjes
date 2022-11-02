package client.game.board.controller;

import domain.game.model.TicTacToe;
import domain.player.model.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

import java.util.Objects;
import java.util.Optional;

public class TicTacToeController {

    @FXML ProgressBar timeLeft;
    @FXML BorderPane container;

    @FXML Label player_1;
    @FXML Label player_2;
    @FXML Label player_1_turn;
    @FXML Label player_2_turn;

    @FXML Button btn_0;
    @FXML Button btn_1;
    @FXML Button btn_2;
    @FXML Button btn_3;
    @FXML Button btn_4;
    @FXML Button btn_5;
    @FXML Button btn_6;
    @FXML Button btn_7;
    @FXML Button btn_8;

    @FXML VBox boardContainer;

    private final TicTacToe ticTacToe = new TicTacToe();

    public void initialize() {
        player_1.setText(ticTacToe.player.getUsername());
        player_2.setText(ticTacToe.opponent.getUsername());

        this.setTurn(ticTacToe.playerToMove);
    }

    public void onMoveClick(ActionEvent event) {
        String index = ((Node)(event.getSource())).getId().split("_")[1];

        System.out.println(index);
    }

    public void onLeaveClick(ActionEvent event) {
        Stage stage = (Stage) container.getScene().getWindow();

        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        Alert alert = new Alert(type, "Are you sure? you will lose the game.");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            SceneSwitcher.getInstance().change(SceneEnum.LOBBY);
            //Hier moet client / server de stop game action doen.
        }
    }

    public void setTurn(String username) {
        if (Objects.equals(username, player_1.getText())) {
            this.player_1_turn.setText("your turn");
            this.disableBoard(false);
        } else {
            this.player_2_turn.setText("your turn");
            this.disableBoard(true);
        }
    }


    public void disableBoard(Boolean condition) {
        this.boardContainer.setDisable(condition);
    }

}
