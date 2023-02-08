package Domain.Player.Model;

import client.Application;
import com.github.javafaker.Faker;
import Domain.Player.Exceptions.FailedToCreateAIException;
import Domain.Player.Exceptions.LoginFailedException;
import Support.Actions.LoginServerAction;
import Support.Exceptions.NoServerConnectionException;
import Support.Exceptions.ServerConnectionFailedException;
import Support.Helpers.AIResponseHandler;
import Support.Services.Server;

import java.util.ArrayList;

public class AI extends Player<AI> {
    private Server connection;

    private final ArrayList<String> winLoss = new ArrayList<String>();

    public AI() throws ServerConnectionFailedException, FailedToCreateAIException {
        super(createUsername());

        this.connect();
        this.login(0);

        Application.addAI(this);
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

    public void disconnect() {
        if (this.connection != null) {
            this.connection.disconnect();
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

    public void setWinLoss(String status) {
        this.winLoss.add(status);
    }

    public ArrayList<String> getWinLoss() {
        return this.winLoss;
    }
}
