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

    private void closeAll() {
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
}
