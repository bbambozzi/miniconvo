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
        System.out.println("SERVER: Closing all server resources..");
        try {
            if (Objects.nonNull(serverSocket)) {
                serverSocket.close();
            }
        } catch (IOException ignored) {
        }
    }

    public void start() {
        while (!serverSocket.isClosed()) {
            try {
                Socket connectedClient = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(connectedClient);
                Thread.ofVirtual().name(clientHandler.getUsername()).start(clientHandler);
            } catch (IOException e) {
                closeAllResources();
            }
        }
    }

}
