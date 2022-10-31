package support.helpers;

import domain.player.model.Player;

public class Auth {
    private static Player player;

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        Auth.player = player;
    }

    public static Boolean check() {
        return Auth.player != null;
    }
}
