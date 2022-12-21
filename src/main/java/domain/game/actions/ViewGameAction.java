package domain.game.actions;

import com.google.gson.JsonObject;
import domain.player.model.Player;
import support.abstracts.AbstractGameAction;

public class ViewGameAction extends AbstractGameAction {
    private final JsonObject data;

    public ViewGameAction(JsonObject data) {
        this.data = data;

        this.handler();
    }

    public ViewGameAction(JsonObject data, Player<?> player) {
        this.data = data;
        this.player = player;

        this.handler();
    }

    @Override
    protected void handler() {
        Player toStart = new Player(data.get("PLAYERTOMOVE").getAsString());
        Player opponent = new Player(data.get("OPPONENT").getAsString());

        this.player.getGame().start(opponent, toStart);
    }
}
