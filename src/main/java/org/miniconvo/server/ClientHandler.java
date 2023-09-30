package org.miniconvo.server;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientHandler implements Runnable {
    private static final Set<ClientHandler> clientHandlerList = new HashSet<>();
    private final Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String username;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = reader.readLine();
            clientHandlerList.add(this);
        } catch (Exception e) {
            closeAll();
        }
    }


    @Override
    public void run() {
        String messageReceivedFromClient;
        while (socket.isConnected()) {
            try {
                messageReceivedFromClient = reader.readLine();
                sendMessage(messageReceivedFromClient);
            } catch (IOException e) {
                closeAll();
                break;
            }
        }

    }

    public void sendMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlerList) {
            if (!clientHandler.username.equals(this.username)) {
                try {
                    clientHandler.writer.write(messageToSend);
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    closeAll();
                }
            }
        }
    }


    void closeAll() {
        try {
            if (Objects.nonNull(socket)) {
                socket.close();
            }
            if (Objects.nonNull(reader)) {
                reader.close();
            }
            if (Objects.nonNull(writer)) {
                writer.close();
            }
            removeClient();
        } catch (Exception ignored) {
        }
    }

    public void removeClient() {
        clientHandlerList.remove(this);
        sendMessage("SERVER MESSAGE: " + this.username + " has left the chat.");
    }
}
