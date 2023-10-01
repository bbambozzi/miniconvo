package org.miniconvo.client;

import java.io.*;
import java.net.Socket;
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
            sendMessageToServer(username);
        } catch (IOException e) {
            closeAll();
            e.printStackTrace();
        }
    }


    private String askForUsernameViaCli() {
        System.out.println("Please enter your username ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        return userInput;
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

    public void listen() {
        System.out.println("Started listening..");
        while (this.socket.isConnected() && this.socket != null) {
            try {
            System.out.println(reader.readLine());
            } catch (IOException e) {
                closeAll();
            }
        }
        System.out.println("Finished listening..");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Please enter your username: ");
        Scanner scanner = new Scanner(System.in);
        String usernameViaCli = scanner.nextLine();
        Socket clientSocketConnection = new Socket("localhost", 1337);
        Client client = new Client(clientSocketConnection, usernameViaCli);
        System.out.println("Connected.");
        client.listen();
    }
}
