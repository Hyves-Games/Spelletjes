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

public class Server {
    private Socket connection;
    private PrintStream data;
    private BufferedReader input;

    public Server getInstance() {
        return this;
    }

    public boolean IsConnected() {
        if (this.connection == null || this.connection.isClosed()) {
            return false;
        }
        return true;
    }

    public Socket getConnection() {
        return this.connection;
    }

    public boolean connect(String IP, int Port) {
        // connection logic
        try {
            this.connection = new Socket(IP, Port);
            this.data = new PrintStream(this.connection.getOutputStream());
            this.input = new BufferedReader(new InputStreamReader((this.connection.getInputStream())));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean disconnect() {
        // disconnect logic
        try {
            this.connection.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public JsonObject read() {
        // read logic
        try {
            return this.parse(this.input.readLine());
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

     private JsonObject parse(String input) {
        String json_string = this.getJsonString(input);
        if (json_string != null) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(json_string, JsonObject.class);
            return jsonObject;
        }
        return null;
    }

    private String getJsonString(String input) {
        Pattern pattern = Pattern.compile("\\{.*?\\}|\\[.*?\\]");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
