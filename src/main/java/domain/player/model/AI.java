package domain.player.model;

import client.Application;
import com.github.javafaker.Faker;
import domain.player.exceptions.FailedToCreateAIException;
import domain.player.exceptions.LoginFailedException;
import support.actions.LoginServerAction;
import support.enums.GameStrategyEnum;
import support.exceptions.NoServerConnectionException;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.AIResponseHandler;
import support.services.Server;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AI extends Player<AI> {
    private Server connection;

    private final ArrayList<String> winLoss = new ArrayList<String>();

    public AI() throws ServerConnectionFailedException, FailedToCreateAIException {
        super(createUsername());

        this.connect().login(0);

        Application.addAI(this);
    }

    private static String createUsername() {
        Faker faker = new Faker();

        return String.format("%sAI", faker.name().firstName().toLowerCase());
    }

    private AI renewUsername() {
        this.setUsername(createUsername());

        return this;
    }

    private void login(Integer retries) throws FailedToCreateAIException {
        try {
            new LoginServerAction(this.username, this.connection);

            return;
        } catch (LoginFailedException e) {
            if (retries <= 5) {
                this.renewUsername().login(retries + 1);

                return;
            }
        } catch (NoServerConnectionException ignored) {}

        throw new FailedToCreateAIException();
    }

    private AI connect() throws ServerConnectionFailedException {
        if (Server.getConnection().isConnected()) {
            this.connection = new Server().connect();

            Thread thread = new Thread(new AIResponseHandler(this));

            thread.setPriority(Thread.MAX_PRIORITY);
            thread.start();
        }

        return this;
    }

    public void disconnect() {
        if (this.connection != null) {
            this.connection.disconnect();
        }
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
