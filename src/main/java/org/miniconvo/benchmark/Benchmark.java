package org.miniconvo.benchmark;

import org.miniconvo.client.ClientSpawner;

import java.util.List;
import java.util.Random;

public class Benchmark {


    public static String randomizeUsername() {
        Random rand = new Random();
        List<String> firstName = List.of("johnny", "rick", "richard", "tom", "michael");
        List<String> secondName = List.of("bay", "sparrow", "robertson", "smith");
        String stringNumber = String.valueOf(rand.nextInt(150000));
        return randomElementOfList(firstName) + randomElementOfList(secondName) + " n: " + stringNumber;
    }

    public static void benchmark(int amount) {
        for (int i = 0; i < amount; i++) {
            ClientSpawner.spawn(randomizeUsername());
        }
    }

    public static void main(String[] args) {
        int amount = 500;
        if (args.length > 0) {
            amount = Integer.parseInt(args[0]);
        }
        benchmark(amount);
    }

    public static <T> T randomElementOfList(List<T> x) {
        Random rand = new Random();
        return x.get(rand.nextInt(0, x.size()));
    }
}
