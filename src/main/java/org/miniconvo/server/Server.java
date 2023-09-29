package org.miniconvo.server;

import java.net.ServerSocket;
import java.net.Socket;

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
        } catch (Exception e) {
            // TODO log exception
            System.out.println(e.toString());
        }
    }
}

}
