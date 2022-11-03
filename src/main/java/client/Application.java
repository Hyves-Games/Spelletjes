package client;

import domain.player.actions.LoginAction;
import domain.player.exceptions.LoginFailedException;
import domain.setting.enums.Settings;
import domain.setting.model.Setting;
import domain.setting.query.SettingQuery;
import javafx.stage.Stage;
import support.abstracts.AbstractGameBoard;
import support.actions.ConnectServerAction;
import support.enums.DatabaseTableEnum;
import support.enums.SceneEnum;
import support.exceptions.NoServerConnectionException;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.AudioPlayer;
import support.helpers.SceneSwitcher;

import java.sql.SQLException;

public class Application extends javafx.application.Application {
    private static AbstractGameBoard gameBoard;

    @Override
    public void start(Stage stage) throws SQLException {
        new SceneSwitcher(stage).change(SceneEnum.LOGIN);

        Setting setting = new SettingQuery().filterByName("auto_login").findOne();
        if (setting != null) {
            try {
                new LoginAction(setting.getValue());
            } catch (LoginFailedException | NoServerConnectionException e) {
                e.printStackTrace();
            }

            SceneSwitcher.getInstance().change(SceneEnum.LOBBY);
        }

        AudioPlayer.play();
        AudioPlayer.setVolume(Settings.MUSIC_VOLUME_LOBBY.getDoubleValue());

        // initialize loaders
        DatabaseTableEnum.createTables();
    }

    public static void main(String[] args) {
        try {
            new ConnectServerAction(Settings.SERVER_IP_ADDRESS.getValue(), Settings.SERVER_PORT.getIntegerValue());
        } catch (ServerConnectionFailedException ignored) {}

        launch();
    }

    public static AbstractGameBoard getGameBoard() {
        return gameBoard;
    }

    public static void setGameBoard(AbstractGameBoard gameBoard) {
        Application.gameBoard = gameBoard;
    }
}