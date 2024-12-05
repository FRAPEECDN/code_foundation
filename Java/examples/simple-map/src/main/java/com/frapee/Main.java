package com.frapee;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static void printResults(HashMap<Integer, Integer> result) {
        for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
            System.out.println("Number " + entry.getKey() + " appears " + entry.getValue() + " many times");
        }
    }

    public static void main(String[] args) {
        System.out.println("Start");
        int[] input = { 1, 4, 5, 1, 5, 7, 9, 8, 5 };
        HashMap<Integer, Integer> mapNumbers = new HashMap<>();

        for (int i = 0; i < input.length; i++) {
            int number = input[i];
            if (!mapNumbers.containsKey(number)) {
                mapNumbers.put(number, 1);
            } else {
                int prev = mapNumbers.get(number);
                prev++;
                mapNumbers.put(number, prev);
            }
        }

        printResults(mapNumbers);
        System.out.println("End");
    }
}