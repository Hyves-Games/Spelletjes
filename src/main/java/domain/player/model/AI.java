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
import java.util.RandomAccess;

public class AI extends Player<AI> {
    private Server connection;

    protected Timestamp updatedAt;
    protected Timestamp createdAt;
    private final ArrayList<String> winLoss = new ArrayList<String>();

    public AI() throws ServerConnectionFailedException, FailedToCreateAIException {
        super(createUsername());

        if (this.getUsername() == null) {
            this.setUsername(AI.createUsername());
        }

        this.setActive();
    }

    public AI(Player player) {
        this.id = player.getId();
        this.username = player.getUsername();
        this.createdAt = player.getCreatedAt();
        this.updatedAt = player.getUpdatedAt();
        this.setGameStrategy(player.getGameStrategy());

        this.setActive();
    }

    public void setActive() {
        try {
            this.connect();
            this.login(0);

            Application.addAI(this);
        } catch (ServerConnectionFailedException | FailedToCreateAIException e) {
            e.printStackTrace();
        }

    }

    public static String createUsername() {
        Faker faker = new Faker();

        return String.format("b");
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
