package org.miniconvo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public record Server(ServerSocket serverSocket) {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1337);
        Server server = new Server(serverSocket);
        System.out.println("Server starting...");
        server.start();
    }

    private void closeAllResources() {
        try {
            if (Objects.nonNull(serverSocket)) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (!serverSocket.isClosed()) {
            try {
                Socket connectedClient = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(connectedClient); // blocking?
                System.out.println("SERVER LOG: user " + clientHandler.getUsername() + " has joined the server.");
            } catch (IOException e) {
                closeAllResources();
                e.printStackTrace();
            }
        }
    }

}
