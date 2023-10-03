package org.miniconvo.server;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {

    private static final Set<ClientHandler> allClients = ConcurrentHashMap.newKeySet();
    private String username;
    private BufferedWriter writer;
    private BufferedReader reader;
    private Socket socket;


    ClientHandler(Socket socket) {
        try {
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.socket = socket;
            this.username = reader.readLine(); // Extract the username from the first line.
            allClients.add(this);
            broadcastMessage(this.username + " has joined the chat.");
        } catch (IOException e) {
            closeAll();
        }
    }

    private static void writeToClientHandlerWriter(ClientHandler clientHandler, String message) throws IOException {
        clientHandler.writer.write(message);
        clientHandler.writer.newLine();
        clientHandler.writer.flush();
    }

    private String askForUsernameFromConsole() {
        System.out.println("SERVER LOG: Please enter your username");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        System.out.println("SERVER LOG: Received username: " + userInput);
        return userInput;
    }

    private void broadcastMessage(String messageToBroadcast) {
        System.out.println("SERVER BROADCAST: " + messageToBroadcast);
        allClients.stream().parallel().forEach(elem -> {
            try {
                writeToClientHandlerWriter(elem, messageToBroadcast);
            } catch (IOException e) {
                closeAll();
            }
        });
    }

    private void removeClientFromHandlers() {
        allClients.remove(this);
    }

    private void closeAll() {
        removeClientFromHandlers();
        broadcastMessage(this.username + " has left the chat.");
        try {
            if (this.socket != null) {
                this.socket.close();
            }

            if (this.reader != null) {
                this.reader.close();
            }
            if (this.writer != null) {
                this.writer.close();
            }
        } catch (IOException e) {
            System.out.println("Exception when closing ClientHandler!");
            System.out.println(e.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        while (this.socket != null && !this.socket.isClosed() && this.socket.isConnected()) {
            try {
                String userInput = reader.readLine();
                if (Objects.isNull(userInput)) {
                    throw new IOException("Couldn't read userInput in run() thread!");
                }
                if (!userInput.isEmpty()) {
                    broadcastMessage(this.username + ": " + userInput);
                }
            } catch (IOException e) {
                closeAll();
                break;
            }
        }
    }

}
