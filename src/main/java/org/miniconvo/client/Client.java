package org.miniconvo.client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Client {

    private BufferedWriter writer;
    private BufferedReader reader;
    private String username;
    private Socket socket;

    Client(Socket socket, String username) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.socket = socket;
            this.username = username;
            sendInitialUsernameToServer(); // maybe remove this logic inside constructor somehow?
        } catch (IOException e) {
            closeAll();
        }
    }

    /**
     * @param args First argument should always be the client's username.
     * @throws IOException Whenever it fails to read or write to stdin/stdout, it will fail.
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(Arrays.toString(args));
        String usernameViaCli;
        if (args.length > 0) {
            usernameViaCli = args[0];
        } else {
            System.out.println("Please enter your username: ");
            usernameViaCli = scanner.nextLine();
        }
        Socket clientSocketConnection = new Socket("localhost", 1337);
        Client client = new Client(clientSocketConnection, usernameViaCli);
        client.listenForServerMessages(); // non-blocking, new thread spawned
        client.listenForUserInputToSendToServer();
    }

    private void sendInitialUsernameToServer() {
        sendMessageToServer(this.username);
    }

    private String askForUsernameViaCli() {
        System.out.println("Please enter your username ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void closeAll() {
        try {
            if (Objects.nonNull(writer)) {
                writer.close();
            }
            if (Objects.nonNull(reader)) {
                reader.close();
            }
            if (Objects.nonNull(socket)) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToServer(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            closeAll();
        }
    }

    /**
     * Spawns a new thread that handles listening for server messages
     * asynchronously.
     */
    public void listenForServerMessages() {
        System.out.println("Started listening..");
        Thread.ofVirtual().name(this.username + " listener").start(() -> {
            while (this.socket.isConnected() && this.socket != null) {
                try {
                    System.out.println(reader.readLine());
                } catch (IOException e) {
                    closeAll();
                    System.out.println("Exception at listener thread.");
                    break;
                }
            }
        });
    }

    public void listenForUserInputToSendToServer() {
        Scanner scanner = new Scanner(System.in);
        while (socket != null && socket.isConnected() && scanner.hasNext()) {
            String userMessage = scanner.nextLine();
            if (Objects.nonNull(userMessage) && !userMessage.isEmpty()) {
                sendMessageToServer(userMessage);
            }
        }
    }
}
