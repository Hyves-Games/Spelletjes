package Support.Helpers;

import Domain.Game.Actions.EndGameAction;
import Domain.Game.Actions.MoveGameAction;
import Domain.Game.Actions.ViewGameAction;
import Domain.Game.Actions.YourTurnAction;
import Domain.Player.Actions.ChallengeCancelledAction;
import Domain.Player.Actions.PlayerListAction;
import Support.Actions.ChallengeAcceptServerAction;
import Support.Records.ServerResponse;
import Support.Services.Server;

public class ResponseHandler implements Runnable {
    private final Server connection = Server.getConnection();

    @Override
    public void run() {
        while(this.connection.isConnected()) {
            try {
                ServerResponse response = this.connection.read();

                if (response != null) {
                    switch (response.type()) {
                        case YOURTURN -> new YourTurnAction();
                        case MOVE -> new MoveGameAction(response.data());
                        case MATCH -> new ViewGameAction(response.data());
                        case CHALLENGE -> new ChallengeAcceptServerAction(response.data());
                        case CANCELLED -> new ChallengeCancelledAction();
                        case WIN, LOSS, DRAW -> new EndGameAction(response.type());
                        case OK, ERROR -> this.connection.responseHandled();
                        case PLAYERLIST -> new PlayerListAction(response.data());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
