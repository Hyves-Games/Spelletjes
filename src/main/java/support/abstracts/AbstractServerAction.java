package support.abstracts;

import support.exceptions.NoServerConnectionException;
import support.services.Server;

public abstract class AbstractServerAction extends AbstractAction {
    protected Server server = Server.getConnection();

    protected Boolean isConnected() {
        return this.server.isConnected();
    }

    protected Boolean isSuccessFull() {return this.server.isLastResponseSuccessful(); }

    protected void command(String command) throws NoServerConnectionException {
        if (this.isConnected()) {
            this.server.write(command);

            return;
        }

        throw new NoServerConnectionException();
    }


}
