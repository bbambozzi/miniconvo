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
            this.username = askForUsernameFromConsole(); // TODO get this from client perhaps?
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            allClients.add(this);
            broadcastMessage(this.username + " has joined the chat.");
        } catch (IOException e) {
            e.printStackTrace();
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
        System.out.println("SERVER LOG: " + messageToBroadcast);
        allClients.stream().parallel().forEach(elem -> {
            try {
                writeToClientHandlerWriter(elem, messageToBroadcast);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public String getUsername() {
        return username;
    }
}
