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
import support.abstracts.AbstractServerResponse;
import support.enums.ServerResponseEnum;

public class Server {
    private Socket socket;
    private PrintStream data;
    private BufferedReader input;
    private static Server connection;

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

    public AbstractServerResponse read() {
        try {
            System.out.println(this.input.readLine());

            AbstractServerResponse response = this.getServerResponse(this.input.readLine());

            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean write(String data) {
        // write logic
        try {
            this.data.println(data);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private AbstractServerResponse getServerResponse(String response) {
        String category = response.split(" ")[1];
        String subcommand = response.split(" ")[2];

        return switch (category) {
            case "GAME" -> new AbstractServerResponse(this.parse(response), ServerResponseEnum.valueOf(subcommand));
            case "PLAYERLIST" -> new AbstractServerResponse(this.parse(response), ServerResponseEnum.PLAYERLIST);
            default -> new AbstractServerResponse(null, ServerResponseEnum.NONE);
        };
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
