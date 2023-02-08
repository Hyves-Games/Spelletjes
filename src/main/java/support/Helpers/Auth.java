package support.helpers;

import domain.player.model.Player;
import support.enums.GameEnum;
import support.enums.GameModeEnum;

public class Auth {
    private static Auth auth;

    private Player<?> player = null;

    private GameEnum lastGame;
    private GameModeEnum lastGameMode;

    private static Auth getInstance() {
        if (Auth.auth == null) {
            Auth.auth = new Auth();
        }

        return Auth.auth;
    }

    public static void setPlayer(Player<?> player) {
        Auth.getInstance().player = player;
    }

    public static Player<?> getPlayer() {
        return Auth.getInstance().player;
    }

    public static Boolean check() {
        return Auth.getInstance().player != null;
    }

    public static GameEnum getLastGame() {
        return Auth.getInstance().lastGame;
    }

    public static void setLastGame(GameEnum lastGame) {
        Auth.getInstance().lastGame = lastGame;
    }

    public static GameModeEnum getLastGameMode() {
        return Auth.getInstance().lastGameMode;
    }

    public static void setLastGameMode(GameModeEnum lastGameMode) {
        Auth.getInstance().lastGameMode = lastGameMode;
    }

    public static void resetGame() {
        Auth.getInstance().lastGame = null;
        Auth.getInstance().lastGameMode = null;
    }
}
