package org.miniconvo.benchmark;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RandomizedClient implements Runnable {

    String[] processArgs;

    RandomizedClient(String[] processArgs) {
        this.processArgs = processArgs;
    }

    @Override
    public void run() {
        try {
            Process process = new ProcessBuilder().command(processArgs).start();
            OutputStream outputStream = process.getOutputStream();
            InputStream inputStream = process.getInputStream();

            // Write to stdin
            String messageToProgram = "hello, program!";
            outputStream.write(messageToProgram.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Failed at benchmark thread");
        }

    }
}
