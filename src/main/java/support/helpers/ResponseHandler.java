package support.helpers;

import client.Application;
import domain.game.actions.ViewGameAction;
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
                        case MATCH -> new ViewGameAction(response.getData());
                        case YOURTURN -> Application.getGameBoard().setYourTurn(true);
                        case WIN -> {}
                        case DRAW -> {}
                        case LOSS -> {}
                        case MOVE -> {}
                        case PLAYERLIST -> {}
                        case OK, ERROR -> this.server.responseHandled();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
