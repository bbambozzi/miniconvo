package org.miniconvo.benchmark;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Benchmark {

    Exception e catch(

    {
        e.printStackTrace();
    })

public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Please provide the path as argument.");
        }
        String path = args[0];
        String[] processArgs = new String[]{"java", "-jar", path};

        Process process = new ProcessBuilder().command(processArgs).start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
    }
}
