package support.helpers;

import domain.game.actions.EndGameAction;
import domain.game.actions.MoveGameAction;
import domain.game.actions.ViewGameAction;
import domain.game.actions.YourTurnAction;
import domain.player.model.AI;
import support.actions.ChallengeAcceptServerAction;
import support.services.Server;

public class AIResponseHandler implements Runnable {
    private final AI player;
    private final Server connection;

    public AIResponseHandler(AI player) {
        this.player = player;
        this.connection = player.getConnection();
    }

    @Override
    public void run() {
        while(this.connection.isConnected()) {
            try {
                ServerResponse response = this.connection.read();

                if (response != null) {
                    switch (response.getType()) {
                        case YOURTURN -> new YourTurnAction(this.player);
                        case MOVE -> new MoveGameAction(response.getData(), this.player);
                        case MATCH -> new ViewGameAction(response.getData(), this.player);
                        case CHALLENGE -> new ChallengeAcceptServerAction(response.getData(), this.connection);
                        case WIN, LOSS, DRAW -> new EndGameAction(response.getType(), this.player);
                        case OK, ERROR -> this.connection.responseHandled();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
