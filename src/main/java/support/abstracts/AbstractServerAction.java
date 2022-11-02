package support.abstracts;

import support.enums.SceneEnum;
import support.helpers.Auth;
import support.helpers.SceneSwitcher;
import support.services.Server;

public abstract class AbstractServerAction extends AbstractAction {
    protected Server server = Server.getConnection();

    protected Boolean isConnected() {
        return this.server.isConnected();
    }

    protected Boolean isSuccessFull() {return this.server.isLastResponseSuccessful(); }

    protected void command(String command) {
        if (this.isConnected()) {
            this.server.write(command);

            return;
        }

        Auth.setPlayer(null);
        SceneSwitcher.getInstance().change(SceneEnum.LOGIN);
    }
}
