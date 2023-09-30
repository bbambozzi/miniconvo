package org.miniconvo.server;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ClientHandler {

    private static final Set<ClientHandler> allClients = new HashSet<>();
    private String username;
    private BufferedWriter writer;
    private BufferedReader reader;


    ClientHandler(Socket socket) {
        try {
            this.username = askForUsernameFromConsole(); // todo change this
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            allClients.add(this);
            broadcastMessage(this.username + " has joined the chat.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String askForUsernameFromConsole() {
        System.out.println("Please enter your username");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        System.out.println("Received username: " + userInput);
        return userInput;
    }

    private void broadcastMessage(String messageToBroadcast) {
        try {

            for (ClientHandler clientHandler : allClients) {
                clientHandler.writer.write(messageToBroadcast);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }
}
