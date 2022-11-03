package support.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import support.exceptions.ServerConnectionFailedException;
import support.helpers.Auth;
import support.helpers.ServerResponse;
import support.enums.ServerResponseEnum;

public class Server {
    private String host;
    private Integer port;

    private Boolean handled = false;
    private Boolean lastResponseSuccessful = false;

    private Socket socket;
    private PrintStream data;
    private BufferedReader input;

    private static Server connection = null;

    public static Server getConnection() {
        if (Server.connection == null) {
            Server.connection = new Server();
        }

        return Server.connection;
    }

    public Boolean isConnected() { return this.socket != null && !this.socket.isClosed(); }

    public Server connect(String host, int port) throws ServerConnectionFailedException {
        try {
            this.host = host;
            this.port = port;

            this.socket = new Socket(host, port);
            this.data = new PrintStream(this.socket.getOutputStream());
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            return this;
        } catch (IOException e) {
            throw new ServerConnectionFailedException();
        }
    }

    public Server connect() throws ServerConnectionFailedException {
        return this.connect(Server.connection.host, Server.connection.port);
    }

    public void disconnect() {
        try {
            this.socket.close();

            Auth.setPlayer(null);
        } catch (IOException ignored) {}
    }

    public ServerResponse read() {
        try {
            return this.getServerResponse(this.input.readLine());
        } catch (Exception e) {
            return null;
        }
    }

    public void write(String data) {
        int waited = 0;
        int sleep = 2;

        this.handled = false;
        this.lastResponseSuccessful = false;

        this.data.println(data);

        while (!this.handled) {
            if (waited >= 2000) {
                break;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ignored) {}

            waited += sleep;
        }
    }

    private ServerResponse getServerResponse(String response) {
        try {
            String[] split = response.split(" ");

//            System.out.println(this.socket.getLocalPort() + " -> " + response);

            switch (split[0]) {
                case "OK":
                    this.lastResponseSuccessful = true;

                    return new ServerResponse(null, ServerResponseEnum.OK);
                case "ERR":
                    this.lastResponseSuccessful = false;

                    return new ServerResponse(null, ServerResponseEnum.ERROR);
                case "SVR":
                    String type = !Objects.equals(split[1], "GAME") ? split[1] : split[2];

                    return new ServerResponse(this.parse(response), ServerResponseEnum.valueOf(type));
                default:
                    return new ServerResponse(null, ServerResponseEnum.NONE);
            }
        } catch (IllegalArgumentException $e) {
            return new ServerResponse(null, ServerResponseEnum.NONE);
        }
    }

     private JsonObject parse(String input) {
        String json_string = this.getJsonString(input);
        if (json_string != null) {
            Gson gson = new Gson();

            return gson.fromJson(json_string, JsonObject.class);
        }
        return null;
    }

    private String getJsonString(String input) {
        Pattern pattern = Pattern.compile("\\{.*?\\}|\\[.*?\\]");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            if (input.contains("PLAYERLIST")) {
                return "{ players: ".concat(matcher.group()).concat("}");
            }

            return matcher.group();
        }

        return null;
    }

    public void responseHandled() {
        this.handled = true;
    }

    public boolean isLastResponseSuccessful() {
        return this.lastResponseSuccessful;
    }
}
