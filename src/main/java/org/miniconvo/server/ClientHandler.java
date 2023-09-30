package org.miniconvo.server;

import java.net.Socket;
import java.util.Scanner;

public class ClientHandler {

    private final String username;

    ClientHandler(Socket socket) {
        this.username = askForUsernameFromConsole(); // todo change this
    }


    private String askForUsernameFromConsole() {
        System.out.println("Please enter your username");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        System.out.println("Received username: " + userInput);
        return userInput;
    }

    public String getUsername() {
        return username;
    }
}
