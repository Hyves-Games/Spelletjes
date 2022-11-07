package support.enums;

import support.helpers.SceneSwitcher;
import support.records.ClientScene;

public enum SceneEnum {
    LOGIN(new ClientScene("authenticator", "login", "Login")),
    LOBBY(new ClientScene("lobby", "lobby", "Lobby")),
    TIC_TAC_TOE(new ClientScene("game/board", "ticTacToe", "Tic-Tac-Toe")),
    SETTING(new ClientScene("settings", "settings", "Settings")),
    PLAYER_LIST(new ClientScene("playerList", "playerList","Players list")),
    GAME_SELECTOR(new ClientScene("game","gameSelector", "Game")),
    GAME_MODE_SELECTOR(new ClientScene("game","gameModeSelector", "Game mode")),
    WAIT_ROOM(new ClientScene("waitingRoom", "waitingRoom","Finding a game")),
    TOURNAMENT_ROOM(new ClientScene("waitingRoom", "waitingTournamentRoom", "Tournament"));

    private final ClientScene clientScene;

    SceneEnum(ClientScene clientScene) {
        this.clientScene = clientScene;
    }

    public ClientScene getClientScene() {
        return this.clientScene;
    }

    public void switchTo() {
        SceneSwitcher.getInstance().change(this);
    }
}
