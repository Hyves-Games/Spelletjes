package support.helpers;

import domain.game.actions.EndGameAction;
import domain.game.actions.MoveGameAction;
import domain.game.actions.ViewGameAction;
import domain.game.actions.YourTurnAction;
import support.services.Server;

public class ResponseHandler extends Thread {
    private final Server server = Server.getConnection();

    @Override
    public void run() {
        while(this.server.isConnected()) {
            try {
                ServerResponse response = this.server.read();

                if (response != null) {
                    switch (response.getType()) {
                        case YOURTURN -> new YourTurnAction();
                        case MOVE -> new MoveGameAction(response.getData());
                        case MATCH -> new ViewGameAction(response.getData());
                        case WIN, LOSS, DRAW -> new EndGameAction(response.getType());
                        case OK, ERROR -> this.server.responseHandled();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
