package domain.player.model;

public class Player {
    private final Boolean AI;
    private final String username;

    public Player(String username, boolean AI) {
        this.AI = AI;
        this.username = username;
    }

    public Player(String username) {
        this(username, false);
    }

    public String getUsername() {
        return this.username;
    }

    public Boolean isAI() {
        return this.AI;
    }
}
