package Support.Actions;

import Support.Abstracts.AbstractServerAction;
import Support.Exceptions.ServerConnectionFailedException;
import Support.Helpers.ResponseHandler;

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
