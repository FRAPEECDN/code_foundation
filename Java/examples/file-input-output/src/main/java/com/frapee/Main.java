package com.frapee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void processFile(String filename, TreeMap<String, Integer> map) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                while (br.ready()) {
                    processline(br.readLine(), map);
                }
            } 
        } catch (FileNotFoundException fe) {
            System.out.println("File not found");
        } catch (IOException ie) {
            System.out.println("File write error");
        }
    }

    public static void processline(String line, TreeMap<String, Integer> map) {
        Pattern p = Pattern.compile("\\b\\w+\\b");
        Matcher m = p.matcher(line);
        while (m.find()) {
            String word = m.group(0);
            if (map.containsKey(word)) {
                int cnt = map.get(word);
                cnt++;
                map.put(word, cnt);
            } else {
                map.put(word, 1);
            }
        }
    }

    public static void switchMapping(TreeMap<String, Integer> map, TreeMap<Integer, ArrayList<String>> countMap) {
        for (Map.Entry<String, Integer> iter : map.entrySet()) {
           if (countMap.containsKey(iter.getValue())) {
                countMap.get(iter.getValue()).add(iter.getKey());
           } else {
                ArrayList<String> newList = new ArrayList<>();
                newList.add(iter.getKey());
                countMap.put(iter.getValue(), newList);
           }
        }
    }

    public static void createFile(String filename, TreeMap<Integer, ArrayList<String>> countMap) {
        try {        
            try (BufferedWriter bf = new BufferedWriter(new FileWriter(filename))) {
                for (Map.Entry<Integer, ArrayList<String>> iter : countMap.entrySet()) {
                    bf.write("Counted ");
                    bf.write(Integer.toString(iter.getKey()));
                    bf.write(" the following words : ");
                    for (String word : iter.getValue()) {
                        bf.write(word);
                        bf.write(", ");
                    }
                    bf.newLine();
                    bf.flush();
                }                
            }
        } catch (FileNotFoundException fe) {
            System.out.println("File not found");
        } catch (IOException ie) {
            System.out.println("File write error");
        }
    }

    public static void main(String[] args) {
        TreeMap<String, Integer> smallMap = new TreeMap<>();
        TreeMap<String, Integer> mediumMap = new TreeMap<>();
        TreeMap<String, Integer> largeMap = new TreeMap<>();
        TreeMap<Integer, ArrayList<String>> countSmallMap = new TreeMap<>();
        TreeMap<Integer, ArrayList<String>> countMediumMap = new TreeMap<>();
        TreeMap<Integer, ArrayList<String>> countLargeMap = new TreeMap<>();

        System.out.println("File Processing");
        String executionPath = System.getProperty("user.dir");
        System.out.print("Executing at =>"+executionPath.replace("\\", "/"));
        System.out.println("==================");
        System.out.println("Process Small File");
        System.out.println("==================");
        processFile("../../data/small_input.txt", smallMap);
        switchMapping(smallMap, countSmallMap);
        createFile("small_output.txt", countSmallMap);
        System.out.println("Process Medium File");
        System.out.println("===================");
        processFile("../../data/medium_input.txt", mediumMap);
        switchMapping(mediumMap, countMediumMap);
        createFile("medium_output.txt", countMediumMap);
        System.out.println("Process Large File");
        System.out.println("==================");
        processFile("../../data/large_input.txt", largeMap);
        switchMapping(largeMap, countLargeMap);
        System.out.println("File Processing Done");
        createFile("large_output.txt", countLargeMap);
    }
}