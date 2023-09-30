package org.miniconvo.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class MessageListener implements Runnable {
    /**
     * Runs this operation.
     */
    BufferedReader readFrom;
    BufferedWriter writeTo;

    MessageListener(BufferedReader readFrom, BufferedWriter writeTo) {
        this.readFrom = readFrom;
        this.writeTo = writeTo;
    }

    @Override
    public void run() {
    }
}
