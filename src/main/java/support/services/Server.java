package support.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Server {
    private Socket connection;
    private PrintStream data;
    private BufferedReader input;

    public Server getInstance() {
        return this;
    }

    public boolean IsConnected() {
        return this.connection != null && !this.connection.isClosed() && this.read() != null;
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

    public String read() {
        // read logic
        try {
            return this.input.readLine();
        } catch (IOException e) {
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
}
