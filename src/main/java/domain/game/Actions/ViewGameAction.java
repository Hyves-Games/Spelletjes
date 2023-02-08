package Domain.Game.Actions;

import com.google.gson.JsonObject;
import Domain.Game.Factories.GameFactory;
import Domain.Game.Model.Reversi;
import Domain.Player.Model.Player;
import Support.Abstracts.AbstractGameAction;
import Support.Abstracts.AbstractGameBoard;

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
        Player<?> opponent = new Player<>(this.data.get("OPPONENT").getAsString());

        GameFactory game = this.player.getGame();
        AbstractGameBoard<?> gameBoard = game.getGameBoard();

        if (gameBoard instanceof Reversi) {
            gameBoard.setStarter(this.player.getUsername().equals(this.data.get("PLAYERTOMOVE").getAsString()));
        }

        game.start(opponent);

    }
}
