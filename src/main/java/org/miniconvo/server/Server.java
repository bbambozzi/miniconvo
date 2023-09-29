package org.miniconvo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

    ServerSocket serverSocket;

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                // do work
                Socket incomingSocket = serverSocket.accept();
                System.out.println("New client connected");
                // TODO add client handler
            }
        } catch (IOException e) {
            // TODO Remove printing the entire stack trace
            e.printStackTrace();
        }
    }
}

}
