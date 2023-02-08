package client;

import Domain.Player.Actions.LoginAction;
import Domain.Player.Exceptions.LoginFailedException;
import Domain.Player.Model.AI;
import Domain.Player.Model.Player;
import Domain.Setting.Enums.Settings;
import Domain.Setting.Model.Setting;
import Domain.Setting.Query.SettingQuery;
import javafx.stage.Stage;
import Support.Actions.ConnectServerAction;
import Support.Enums.DatabaseTableEnum;
import Support.Enums.SceneEnum;
import Support.Exceptions.NoServerConnectionException;
import Support.Exceptions.ServerConnectionFailedException;
import Support.Helpers.AudioPlayer;
import Support.Helpers.SceneSwitcher;

import java.sql.SQLException;
import java.util.ArrayList;

public class Application extends javafx.application.Application {
    private static final ArrayList<AI> AIList = new ArrayList<AI>();

    @Override
    public void start(Stage stage) {
        new SceneSwitcher(stage).change(SceneEnum.LOGIN);

        Setting setting = new SettingQuery().filterByName("auto_login").findOne();
        if (setting != null) {
            try {
                new LoginAction(setting.getStringValue());

                SceneEnum.LOBBY.switchTo();
            } catch (LoginFailedException | NoServerConnectionException ignored) {}
        }

        AudioPlayer.play();
        AudioPlayer.setVolume(Settings.MUSIC_VOLUME_LOBBY.getDoubleValue());
    }

    public static void main(String[] args) throws SQLException {
        DatabaseTableEnum.createTables();
        try {
            new ConnectServerAction(Settings.SERVER_IP_ADDRESS.getStringValue(), Settings.SERVER_PORT.getIntegerValue());
        } catch (ServerConnectionFailedException ignored) {}

        launch();
    }

    public static void addAI(AI player) {
        AIList.add(player);
    }

    public static void removeAI(Player player) {
        for (AI AIPlayer : AIList) {
            if (AIPlayer.getUsername().equals(player.getUsername())) {
                AIPlayer.disconnect();

                AIList.remove(AIPlayer);

                return;
            }
        }
    }
}