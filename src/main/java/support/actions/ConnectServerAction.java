package support.actions;

import support.abstracts.AbstractServerAction;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.ResponseHandler;

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
            this.connection.disconnect();
        }

        this.connection.connect(this.host, this.port);

        new Thread(new ResponseHandler()).start();
    }
}
