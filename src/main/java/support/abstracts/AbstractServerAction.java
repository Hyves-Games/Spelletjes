package support.abstracts;

import support.exceptions.NoServerConnectionException;
import support.services.Server;

public abstract class AbstractServerAction extends AbstractAction {
    protected Server server = Server.getConnection();

    protected AbstractServerResponse response;

    protected Boolean isConnected() {
        return this.server.isConnected();
    }

    protected void command(String command) throws NoServerConnectionException {
        if (this.isConnected()) {
            this.server.write(command);

            this.response = this.server.read();

            return;
        }

        throw new NoServerConnectionException();
    }
}
