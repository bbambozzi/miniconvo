package org.miniconvo.server;

import java.net.ServerSocket;
import java.net.Socket;

public record Server(ServerSocket serverSocket) {

    public void start() {
        while (!serverSocket.isClosed()) {
            Socket connectedClient = serverSocket.accept();
        }
    }

}
