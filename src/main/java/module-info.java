module hyvesgamesspelletjes {
    requires javafx.fxml;
    requires javafx.controls;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens client to javafx.fxml;

    exports client;
}