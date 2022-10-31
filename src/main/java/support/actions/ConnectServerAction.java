package support.actions;

import support.abstracts.AbstractServerAction;
import support.exceptions.ServerConnectionFailedException;

public class ConnectServerAction extends AbstractServerAction {
    private final String host;
    private final Integer port;

    public ConnectServerAction(String host, Integer port) throws ServerConnectionFailedException {
        this.host = host;
        this.port = port;

        this.handler();
    }

    @Override
    protected void handler() throws ServerConnectionFailedException {
        boolean success = this.server.connect(this.host, this.port);

        if (success) {
            System.out.println("Connection established");

            return;
        }

        throw new ServerConnectionFailedException();
    }
}
