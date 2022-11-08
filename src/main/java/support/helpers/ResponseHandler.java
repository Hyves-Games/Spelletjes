package support.helpers;

import domain.game.actions.EndGameAction;
import domain.game.actions.MoveGameAction;
import domain.game.actions.ViewGameAction;
import domain.game.actions.YourTurnAction;
import domain.player.actions.PlayerListAction;
import support.services.Server;

public class ResponseHandler implements Runnable {
    private final Server connection = Server.getConnection();

    @Override
    public void run() {
        while(this.connection.isConnected()) {
            try {
                ServerResponse response = this.connection.read();

                if (response != null) {
                    switch (response.getType()) {
                        case YOURTURN -> new YourTurnAction();
                        case MOVE -> new MoveGameAction(response.getData());
                        case MATCH -> new ViewGameAction(response.getData());
                        case CHALLENGE -> System.out.println(response.getData().toString());
                        case WIN, LOSS, DRAW -> new EndGameAction(response.getType());
                        case OK, ERROR -> this.connection.responseHandled();
                        case PLAYERLIST -> new PlayerListAction(response.getData());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
