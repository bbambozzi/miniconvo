package org.miniconvo.client;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private String username;

    public Client(Socket socket, String username) {
        try {

            this.socket = socket;
            this.username = username;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeAll();
        }
    }

    public void closeAll() {
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
        } catch (Exception ignored) {
        }
    }

    private void sendMessage() {
        try {
            writer.write(username);
            writer.newLine();
            writer.flush();
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String userMessage = scanner.nextLine();
                writer.write(username + ": " + userMessage);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            closeAll();
        }
    }


    public void listenToMessages() {
        while (socket.isConnected()) {
            MessageListener messageListener = new MessageListener(this);
            messageListener.run();
        }
    }


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Initialized Client Program");
        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();
        Socket clientSocket = new Socket("localhost", 1337);
        Client client = new Client(clientSocket, username);
        client.listenToMessages();
        client.sendMessage();
    }
}
