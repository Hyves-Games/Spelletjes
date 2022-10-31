module hyvesgamesspelletjes {
    requires javafx.fxml;
    requires javafx.controls;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens client.authenticator.controller to javafx.fxml;
    opens client.menu.controller to javafx.fxml;
    opens client.waitingRoom.controller to javafx.fxml;
    opens client.settings.controller to javafx.fxml;
    opens client.game.controller to javafx.fxml;

    exports client;
}