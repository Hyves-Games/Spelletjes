package client;

import domain.setting.model.Setting;
import domain.setting.query.SettingQuery;
import javafx.stage.Stage;
import support.actions.ConnectServerAction;
import support.enums.DatabaseTableEnum;
import support.enums.SceneEnum;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.AudioPlayer;
import support.helpers.SceneSwitcher;

import java.sql.SQLException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws SQLException, NoSuchFieldException, IllegalAccessException {
        new SceneSwitcher(stage).change(SceneEnum.LOGIN);

        SettingQuery settingQuery = new SettingQuery();
        System.out.println(settingQuery.where("name", "=", "Appel").find()[0].getId());

        AudioPlayer.play();
        AudioPlayer.setVolume(0);

        // initialize loaders
        DatabaseTableEnum.createTables();
    }

    public static void main(String[] args) {
        try {
            new ConnectServerAction("localhost", 7789);
        } catch (ServerConnectionFailedException ignored) {}

        launch();
    }
}