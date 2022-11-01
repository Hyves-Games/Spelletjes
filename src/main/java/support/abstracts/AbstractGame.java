package support.abstracts;

public abstract class AbstractGame {

    public String name;

    public String[] gameModes;

    public String iconPath;

    public String musicPath;

    public String getName() {
        return this.name;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public String getMusicPath() {
        return this.musicPath;
    }

    public String[] getGameModes() {
        return this.gameModes;
    }

}
