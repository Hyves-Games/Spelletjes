package support.actions;

import support.abstracts.AbstractServerAction;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.ResponseHandler;

import java.util.Set;

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
        if (this.isConnected()) {
            this.server.disconnect();
        }

        boolean success = this.server.connect(this.host, this.port);

        if (!success) {
            throw new ServerConnectionFailedException();
        }

        new Thread(new ResponseHandler()).start();
    }
}
