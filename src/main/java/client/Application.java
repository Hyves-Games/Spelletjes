package client;

import domain.player.actions.LoginAction;
import domain.player.exceptions.LoginFailedException;
import domain.player.model.AI;
import domain.player.model.Player;
import domain.setting.enums.Settings;
import domain.setting.model.Setting;
import domain.setting.query.SettingQuery;
import javafx.stage.Stage;
import support.actions.ConnectServerAction;
import support.enums.DatabaseTableEnum;
import support.enums.SceneEnum;
import support.exceptions.NoServerConnectionException;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.AudioPlayer;
import support.helpers.SceneSwitcher;

import java.sql.SQLException;
import java.util.ArrayList;

public class Application extends javafx.application.Application {
    private static ArrayList<AI> AIList = new ArrayList<AI>();

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