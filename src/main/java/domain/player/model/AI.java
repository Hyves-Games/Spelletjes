package domain.player.model;

import com.github.javafaker.Faker;
import domain.player.exceptions.FailedToCreateAIException;
import domain.player.exceptions.LoginFailedException;
import support.actions.LoginServerAction;
import support.exceptions.NoServerConnectionException;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.AIResponseHandler;
import support.services.Server;

public class AI extends Player {
    private Server connection;

    public AI() throws ServerConnectionFailedException, FailedToCreateAIException {
        super(createUsername());

        this.connect();
        this.login(0);
    }

    private static String createUsername() {
        Faker faker = new Faker();

        return String.format("%sAI", faker.name().firstName().toLowerCase());
    }

    private void connect() throws ServerConnectionFailedException {
        if (Server.getConnection().isConnected()) {
            this.connection = new Server().connect();

            Thread thread = new Thread(new AIResponseHandler(this));

            thread.setPriority(Thread.MAX_PRIORITY);
            thread.start();
        }
    }

    private void login(Integer retries) throws FailedToCreateAIException {
        try {
            new LoginServerAction(this.username, this.connection);

            return;
        } catch (LoginFailedException e) {
            if (retries <= 5) {
                this.login(retries + 1);

                return;
            }
        } catch (NoServerConnectionException ignored) {}

        throw new FailedToCreateAIException();
    }

    public Server getConnection() {
        return connection;
    }
}
