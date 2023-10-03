package org.miniconvo.benchmark;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Benchmark {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Please provide the path as argument.");
        }
        String path = args[0];
        int threadsToSpawn = Integer.parseInt(args[1]);
        String[] processArgs = new String[]{"java", "-jar", path};
        RandomizedClient randomizedClient = new RandomizedClient(processArgs);
        System.out.println("Starting threads..");
        for (int i = 0; i < threadsToSpawn; i++) {
            Thread.ofVirtual().start(randomizedClient);
        }

    }
}
