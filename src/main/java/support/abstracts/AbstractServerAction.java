package support.abstracts;

import support.enums.SceneEnum;
import support.exceptions.NoServerConnectionException;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;
import support.services.Server;

public abstract class AbstractServerAction extends AbstractAction {
    protected Boolean skip = false;
    protected Server connection = Server.getConnection();

    protected Boolean isConnected() {
        return this.connection.isConnected();
    }

    protected Boolean isSuccessFull() {return this.connection.isLastResponseSuccessful(); }

    protected void command(String command) throws NoServerConnectionException {
        if (this.isConnected()) {
            this.connection.write(command, this.skip);

            return;
        }

        if (SceneSwitcher.getInstance().getScene() == SceneEnum.LOGIN) {
            throw new NoServerConnectionException();
        } else {
            Auth.setPlayer(null);
            SceneEnum.LOGIN.switchTo();
        }
    }
}
