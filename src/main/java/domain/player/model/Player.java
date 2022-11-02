package domain.player.model;

import support.enums.GameModeEnum;

public class Player {
    private final Boolean AI;
    private final String username;

    private GameModeEnum lastGameMode;

    public Player(String username, boolean AI) {
        this.AI = AI;
        this.username = username;
    }

    public Player(String username) {
        this(username, false);
    }

    public String getUsername() {
        return this.username;
    }

    public Boolean isAI() {
        return this.AI;
    }

    public GameModeEnum getLastGameMode() {
        return this.lastGameMode;
    }

    public Player setLastGameMode(GameModeEnum lastGameMode) {
        this.lastGameMode = lastGameMode;

        return this;
    }
}
