package support.abstracts;

import support.exceptions.NoServerConnectionException;
import support.exceptions.ServerConnectionFailedException;

public abstract class AbstractAction {
    protected abstract void handler() throws Exception;
}
