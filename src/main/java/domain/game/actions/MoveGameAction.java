package domain.game.actions;

import client.Application;
import com.google.gson.JsonObject;
import support.abstracts.AbstractAction;
import support.helpers.Auth;

public class MoveGameAction extends AbstractAction {
    private final JsonObject data;

    public MoveGameAction(JsonObject data) {
        this.data = data;

        try {this.handler();} catch (Exception ignored) {}
    }

    @Override
    protected void handler() throws Exception {
        String username = this.data.get("PLAYER").getAsString();

        Integer value = username.equals(Auth.getPlayer().getUsername()) ? 1 : -1;

        Application.getGameBoard().setMove(this.data.get("MOVE").getAsInt(), value);
    }
}
