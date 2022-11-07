module hyvesgamesspelletjes {
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.google.gson;
    requires javafaker;
    requires java.sql;
    requires org.jetbrains.annotations;

    opens client.authenticator.controller to javafx.fxml;
    opens client.lobby.controller to javafx.fxml;
    opens client.waitingRoom.controller to javafx.fxml;
    opens client.settings.controller to javafx.fxml;
    opens client.game.controller to javafx.fxml;
    opens client.playerList.controller to javafx.fxml;
    opens client.game.board.controller to javafx.fxml;

    exports client;
}
