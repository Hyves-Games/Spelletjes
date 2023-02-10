package support.actions;

import support.enums.GameEnum;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;
import com.google.gson.JsonObject;
import domain.player.model.AI;
import support.abstracts.AbstractServerAction;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

public class ChallengeAcceptServerAction extends AbstractServerAction {
    private final JsonObject data;

    public ChallengeAcceptServerAction(JsonObject data) {
        this.data = data;

        GameEnum game = GameEnum.getByKey(this.data.get("GAMETYPE").getAsString());

        if (game != null) {
            Platform.runLater(() -> {
                SceneSwitcher scene = SceneSwitcher.getInstance();

                ButtonType lobbyButton = new ButtonType("Decline", ButtonBar.ButtonData.NO);
                ButtonType rematchButton = new ButtonType("Accept", ButtonBar.ButtonData.YES);

                String message = String.format("You have been challenged by %s", this.data.get("CHALLENGER").getAsString());

                Alert alert = new Alert(Alert.AlertType.NONE, message, lobbyButton, rematchButton);

                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initOwner(scene.getStage());

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent()) {
                    ButtonBar.ButtonData type = result.get().getButtonData();

                    if (type.equals(ButtonBar.ButtonData.YES)) {
                        Auth.setLastGame(game);
                        Auth.setLastGameMode(GameModeEnum.PVP);

                        game.create().setAuthPlayer();

                        this.handler();

                        scene.removeDialog("endScreenAlert");
                    }

                    if (type.equals(ButtonBar.ButtonData.NO)) {
                        SceneEnum.LOBBY.switchTo();

                        Auth.resetGame();
                    }
                }
            });
        }
    }

    public ChallengeAcceptServerAction(JsonObject data, AI player) {
        this.data = data;
        this.skip = true;
        this.connection = player.getConnection();

        Auth.getLastGame().create().setPlayer(player);

        this.handler();
    }

    @Override
    protected void handler() {
        Integer ChallengeNumber = this.data.get("CHALLENGENUMBER").getAsInt();

        try {
            this.command(String.format("challenge accept %d", ChallengeNumber));
        } catch (NoServerConnectionException ignored) {}
    }
}
