package domain.game.actions;

import com.google.gson.JsonObject;
import domain.player.model.Player;
import support.abstracts.AbstractGameAction;

public class MoveGameAction extends AbstractGameAction {
    private final JsonObject data;

    public MoveGameAction(JsonObject data) {
        this.data = data;

        this.handler();
    }

    public MoveGameAction(JsonObject data, Player<?> player) {
        this.data = data;
        this.player = player;

        this.handler();
    }

    @Override
    protected void handler() {
        String username = this.data.get("PLAYER").getAsString();
        Integer value = username.equals(player.getUsername()) ? 1 : -1;

        player.getGameBoard().setMove(this.data.get("MOVE").getAsInt(), value);
    }
}
