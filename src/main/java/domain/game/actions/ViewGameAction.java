package domain.game.actions;

import com.google.gson.JsonObject;
import domain.game.model.Game;
import domain.game.model.TicTacToe;
import domain.player.model.Player;
import domain.setting.enums.Settings;
import support.abstracts.AbstractGameAction;
import support.enums.GameModeEnum;
import support.helpers.Auth;

public class ViewGameAction extends AbstractGameAction {
    private final JsonObject data;

    public ViewGameAction(JsonObject data) {
        this.data = data;

        this.handler();
    }

    public ViewGameAction(JsonObject data, Player player) {
        this.data = data;
        this.player = player;

        this.handler();
    }

    @Override
    protected void handler() {
        Player opponent = new Player(data.get("OPPONENT").getAsString());

        if (Settings.TOURNAMENT.getBooleanValue()) {
            Game game = Auth.getPlayer().getLastGameMode().create();
            game.setPlayer(this.player);
            this.player.setGame(game);
            game.start(opponent);
        } else {
            this.player.getGame().start(opponent);
        }
    }
}
