package org.miniconvo.client;

import java.net.Socket;

public class ClientSpawner {

    public static void spawn(String username) {
        try {
        Socket clientSocketConnection = new Socket("localhost", 1337);
        Client client = new Client(clientSocketConnection, username);
        client.listenForServerMessages(); // non-blocking, new thread spawned
        } catch (Exception e) {
            System.out.println("Error spawning new client..");
        }
    }
}
