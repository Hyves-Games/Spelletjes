package Support.Helpers;

import Domain.Game.Actions.EndGameAction;
import Domain.Game.Actions.MoveGameAction;
import Domain.Game.Actions.ViewGameAction;
import Domain.Game.Actions.YourTurnAction;
import Domain.Player.Model.AI;
import Support.Actions.ChallengeAcceptServerAction;
import Support.Records.ServerResponse;
import Support.Services.Server;

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
                    switch (response.type()) {
                        case YOURTURN -> new YourTurnAction(this.player);
                        case MOVE -> new MoveGameAction(response.data(), this.player);
                        case MATCH -> new ViewGameAction(response.data(), this.player);
                        case CHALLENGE -> new ChallengeAcceptServerAction(response.data(), this.player);
                        case WIN, LOSS, DRAW -> new EndGameAction(response.type(), this.player);
                        case OK, ERROR -> this.connection.responseHandled();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
