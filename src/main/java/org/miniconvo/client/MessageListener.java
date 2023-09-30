package org.miniconvo.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class MessageListener implements Runnable {
    private final Client client;
    /**
     * Runs this operation.
     */
    BufferedReader readFrom;
    BufferedWriter writeTo;
    Socket socket;

    MessageListener(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String messageInServer = readFrom.readLine();
                // TODO change this to a online chatroom or something instead of the cli
                System.out.println(messageInServer); // prints message from server in terminal
            } catch (Exception e) {
                client.closeAll();
            }
        }
    }
}
