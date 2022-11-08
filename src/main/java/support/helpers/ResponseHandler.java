package support.helpers;

import domain.game.actions.EndGameAction;
import domain.game.actions.MoveGameAction;
import domain.game.actions.ViewGameAction;
import domain.game.actions.YourTurnAction;
import support.records.ServerResponse;
import support.services.Server;

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
                        case CHALLENGE -> System.out.println(response.data().toString());
                        case WIN, LOSS, DRAW -> new EndGameAction(response.type());
                        case OK, ERROR -> this.connection.responseHandled();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
