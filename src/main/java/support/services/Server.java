package support.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import support.helpers.ServerResponse;
import support.enums.ServerResponseEnum;

public class Server {
    private Socket socket;
    private PrintStream data;
    private BufferedReader input;
    private static Server connection;
    private boolean lastResponseSuccessful;

    public static Server getConnection() {
        if (Server.connection == null) {
            Server.connection = new Server();
        }

        return Server.connection;
    }

    public boolean isConnected() { return this.socket != null && !this.socket.isClosed(); }

    public boolean connect(String IP, int Port) {
        try {
            this.socket = new Socket(IP, Port);
            this.data = new PrintStream(this.socket.getOutputStream());
            this.input = new BufferedReader(new InputStreamReader((this.socket.getInputStream())));
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public boolean disconnect() {
        try {
            this.socket.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public ServerResponse read() {
        try {
            System.out.println(this.input.readLine());

            ServerResponse response = this.getServerResponse(this.input.readLine());

            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean write(String data) {
        // write logic
        try {
            this.lastResponseSuccessful = false; // reset to false
            this.data.println(data);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    private ServerResponse getServerResponse(String response) {
        try {
            String[] split = response.split(" ");

            switch (split[0]) {
                case "OK":
                    this.lastResponseSuccessful = true;

                    return new ServerResponse(null, ServerResponseEnum.OK);
                case "ERR":
                    this.lastResponseSuccessful = false;

                    return new ServerResponse(null, ServerResponseEnum.ERROR);
                case "SVR":
                    String type = split[1] != "GAME" ? split[1] : split[2];

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
}
