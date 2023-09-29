package org.miniconvo.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket serverSocket;

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                // do work
                try {
                    Socket incomingSocket = serverSocket.accept();
                    System.out.println("New client connected");
                } catch (Exception e) {
                    // TODO log exception
                    System.out.println(e.toString());
                }
            }
        } catch (Exception e) {
            // TODO log exception
            System.out.println(e.toString());
        }
    }
}

}
