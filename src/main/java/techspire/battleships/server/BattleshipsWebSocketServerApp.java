package techspire.battleships.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.glassfish.tyrus.server.Server;

public class BattleshipsWebSocketServerApp {
    public static void main(String[] args) {
        runServer();
    }

    public static void runServer() {
        Server server;
        server = new Server("localhost", 8025, "/ws", null, BattleshipsWebSocketServer.class);

        try {
            server.start();
            System.out.println("WebSocket server started at ws://localhost:8025/ws/websocket");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Please press a key to stop the server...");
            reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
