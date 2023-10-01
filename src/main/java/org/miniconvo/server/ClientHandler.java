package org.miniconvo.server;

import java.io.*;
import java.net.Socket;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class ClientHandler implements Runnable {

    private static final Set<ClientHandler> allClients = new HashSet<>();
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
        broadcastMessage(this.username + " has left the chat.");
        allClients.remove(this);
    }

    private void closeAll() {
        removeClientFromHandlers();
        try {
            this.socket.close();
            this.writer.close();
            this.reader.close();

        } catch (Exception e) {
            e.printStackTrace();
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
                    closeAll();
                    break;
                }
                if (!userInput.isEmpty()) {
                    broadcastMessage(this.username + ": " + userInput);
                }
            } catch (IOException e) {
                System.out.println("Exception and stuff");
                closeAll();
                break;
            }
        }
    }

}
