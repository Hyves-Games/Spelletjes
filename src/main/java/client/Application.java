package client;

import domain.player.model.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import support.helpers.Mediaplayer;
import support.abstracts.AbstractServerResponse;
import support.actions.MoveServerAction;
import support.services.Server;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Application.class.getResource("menu/menu.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        stage.setTitle("Lobby");
        stage.setScene(scene);
        stage.show();

        Mediaplayer.play();
    }

    public static void main(String[] args) {
        new Thread(() -> {
            Server server = Server.getInstance();
            boolean connection = server.connect("localhost", 7789);
            if (connection) {
                System.out.println("Connection established");
            } else {
                System.out.println("Could not create connection");
            }
            if(server.IsConnected()) {
                System.out.println("Connection test successful");
            } else {
                System.out.println("Connection test unsuccessful");
            }
            server.write("login socket");
            server.write("subscribe tic-tac-toe");
            server.write("get playerlist");

            while (server.IsConnected()) {
                AbstractServerResponse response = server.read();
                switch (response.getResponse()) {
                    case NONE:
                        break;
                    case YOURTURN:
                        new MoveServerAction(7);
                        break;
                    default:
                        System.out.printf("Response Type: %s\nResponse Data: %s\n", response.getResponse(), response.getData());
                        break;
                }
            }
        }).start();
        launch();
    }
}