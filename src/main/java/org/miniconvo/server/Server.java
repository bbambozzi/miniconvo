package org.miniconvo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

    Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    private void closeSocket() {

    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                // do work
                Socket acceptedSocket = serverSocket.accept(); // blocks
                System.out.println("New client connected!");
                ClientHandler clientHandler = new ClientHandler(acceptedSocket);
                Thread clientHandlerThread = new Thread(clientHandler);
                clientHandlerThread.start();
            }
        } catch (IOException e) {
            // TODO Remove printing the entire stack trace
            e.printStackTrace();
        }
    }
}

