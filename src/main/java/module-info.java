module hyvesgamesspelletjes {
    requires javafx.fxml;
    requires javafx.controls;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.google.gson;

    opens client.authenticator.controller to javafx.fxml;

    exports client;
}