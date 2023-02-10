package support.abstracts.controllers;

import domain.player.model.AI;
import support.actions.ChallengeServerAction;
import support.actions.StopGameAction;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;
import client.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import support.abstracts.AbstractGameBoard;
import support.helpers.Auth;
import javafx.stage.Modality;

import java.util.Optional;

public abstract class AbstractGameBoardController {
    @FXML public Label player_1;
    @FXML public Label player_2;
    @FXML public Label player_1_turn;
    @FXML public Label player_2_turn;

    @FXML public VBox boardContainer;

    protected Button[] board;

    protected final AbstractGameBoard<?> gameBoard = Auth.getPlayer().getGameBoard();

    public void initialize() {
        this.createBoard();
        this.createLabels();

        this.gameBoard.addEventListenerForTurn(() -> Platform.runLater(this::changeTurn));
        this.gameBoard.addEventListenerForBoard(() -> Platform.runLater(this::changeBoardView));
        this.gameBoard.addEventListenerForEnd(() -> {
            if (this.isPlayerAI()) {
                Platform.runLater(SceneEnum.WAIT_ROOM_TOURNAMENT::switchTo);
            } else {
                Platform.runLater(this::showEndScreen);
            }
        });

        this.changeTurn();
        this.changeBoardView();
    }

    private void createBoard() {
        HBox row = new HBox();
        Integer size = this.gameBoard.getSize();
        Integer sizeX = this.gameBoard.getSizeX();

        Button[] buttons = new Button[size];

        for (int i = 0; i < size; i++) {
            Button btn = new Button();

            btn.setId("btn_" + (i));
            btn.setCursor(Cursor.HAND);
            btn.setOnAction(this::onMoveClick);

            row.getChildren().add(this.createBtn(btn));

            if (i % sizeX == sizeX - 1) {
                boardContainer.getChildren().add(this.createRow(row));

                row = new HBox();
            }

            buttons[i] = btn;
        }

        this.board = buttons;
    }

    public void onMoveClick(ActionEvent event) {
        int index = Integer.parseInt(((Node) (event.getSource())).getId().split("_")[1]);

        this.gameBoard.doMove(index);
    }

    protected Boolean isPlayerAI() {
        return Auth.getPlayer() instanceof AI;
    }

    protected String getPlayerUsername() {
        return this.gameBoard.getPlayer().getUsername();
    }

    protected String getOpponentUsername() {
        return this.gameBoard.getOpponent().getUsername();
    }

    private void setPlayerTurn() {
        this.player_1_turn.setText("Your turn");
        this.player_2_turn.setText(null);

        this.boardContainer.setDisable(this.isPlayerAI());
    }

    private void setOpponentTurn() {
        this.player_1_turn.setText(null);
        this.player_2_turn.setText("Your turn");

        this.boardContainer.setDisable(true);
    }

    private void changeTurn() {
        if (this.gameBoard.isPlayerTurn()) {
            this.setPlayerTurn();
        } else {
            this.setOpponentTurn();
        }
    }

    private void showEndScreen() {
        if(SceneSwitcher.getInstance().getScene() == SceneEnum.LOBBY) {
            return;
        }

        GameModeEnum GameMode = Auth.getLastGameMode();
        SceneSwitcher scene = SceneSwitcher.getInstance();

        ButtonType rematchButton = new ButtonType("Rematch!", ButtonBar.ButtonData.YES);
        ButtonType lobbyButton = new ButtonType("To the lobby", ButtonBar.ButtonData.NO);

        String message = switch (this.gameBoard.getEndState()) {
            case WIN -> "You have won the game!";
            case LOSS -> "You have lost the game from " + this.getOpponentUsername() + ".";
            case DRAW -> "It is a draw!";
        };

        Alert alert = new Alert(Alert.AlertType.NONE, message, lobbyButton, rematchButton);

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(scene.getStage());

        scene.addDialog("endScreenAlert", alert);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            ButtonBar.ButtonData type = result.get().getButtonData();

            if (type.equals(ButtonBar.ButtonData.YES)) {
                GameMode.create(false, false);

                new ChallengeServerAction(this.gameBoard.getOpponent(), Auth.getLastGame().getKey());
            }

            if (type.equals(ButtonBar.ButtonData.NO)) {
                if (GameMode.equals(GameModeEnum.PVA)) {
                    Application.removeAI(this.gameBoard.getOpponent());
                }

                Auth.resetGame();

                SceneEnum.LOBBY.switchTo();
            }

        }
    }

    public void onLeaveClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure? you will lose the game.");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(SceneSwitcher.getInstance().getStage());

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            new StopGameAction();

            if (Auth.getLastGameMode().equals(GameModeEnum.PVA)) {
                Application.removeAI(this.gameBoard.getOpponent());
            }

            Auth.resetGame();

            SceneEnum.LOBBY.switchTo();
        }
    }

    protected abstract void createLabels();
    protected abstract HBox createRow(HBox row);
    protected abstract Button createBtn(Button btn);

    protected abstract void changeBoardView();}
