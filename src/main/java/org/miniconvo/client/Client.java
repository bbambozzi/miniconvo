package org.miniconvo.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private BufferedWriter writer;
    private BufferedReader reader;
    private String username;

    Client(Socket socket) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = askForUsernameViaCli();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String askForUsernameViaCli() {
        System.out.println("Please enter your username ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        return userInput;
    };
}
