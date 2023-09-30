package org.miniconvo;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        ServerSocket serverSocket = new ServerSocket(1337);
    }
}