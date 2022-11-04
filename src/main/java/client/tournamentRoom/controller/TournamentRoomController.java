package client.tournamentRoom.controller;

import domain.game.model.Game;
import domain.player.exceptions.FailedToCreateAIException;
import domain.player.model.AI;
import domain.setting.enums.Settings;
import support.actions.StopGameAction;
import support.enums.GameModeEnum;
import support.enums.SceneEnum;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.Auth;

public class TournamentRoomController {
    public void initialize() throws ServerConnectionFailedException, FailedToCreateAIException {
        Settings.TOURNAMENT.saveWithValue(true);
        Auth.setPlayer(new AI());
        Auth.getPlayer().setLastGameMode(GameModeEnum.TIC_TAC_TOE);
    }

    public void onCancel() {
        new StopGameAction();

        SceneEnum.LOGIN.switchTo();
    }
}
