package com.frapee;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        // Example multi-line string a text block
        String search = """
        Some people, when confronted with a problem, think 
        I know, I'll use regular expressions.
        Now they have two problems.
                        """;

        // (?i) - ignorecase
        // .*? - allow (optionally) any characters before
        // \b - word boundary
        // %s - variable to be changed by String.format (quoted to avoid regex errors)
        // \b - word boundary
        // .*? - allow (optionally) any characters after
        // Looking for word boundary
        Pattern p = Pattern.compile("\\bregular expressions\\b");
        Matcher m = p.matcher(search);
        m.find();

        System.out.println(m.group() + " has been found");

        // Find word with length between 3 and 8
        p = Pattern.compile("\\b\\w{3,8}\\b");
        m = p.matcher(search);

        while (m.find()) {
            System.out.println("Full match: " + m.group(0));
        }        

        p = Pattern.compile("\\b\\w{7}\\b");
        m = p.matcher(search);
        StringBuilder sb = new StringBuilder();

        while (m.find()) {
            String replacementString = "[" + m.group(0) + "]";
            m.appendReplacement(sb, replacementString);
        }

        // append the remaining part of the input string after the last match
        m.appendTail(sb);
        System.out.println("Replaced string is : " + sb.toString());

        // mustace problem
        TreeMap<String, String> variables = new TreeMap<>();
        variables.put("name", "Bert");
        variables.put("game", "Java frustration");

        p = Pattern.compile("(\\{\\{[a-z]*\\}\\})");
        String mustaceString = "Hello {{name}}, welcome to the mustache game {{game}}";
        m = p.matcher(mustaceString);
        while (m.find()) {
            System.out.println("Found variables: " + m.group(0));
        }

        m = p.matcher(mustaceString);
        sb = new StringBuilder();
        while (m.find()) {
            String lookupKey = m.group(0);
            lookupKey = lookupKey.substring(2, lookupKey.length() - 2);

            String replacementString = variables.get(lookupKey);
            m.appendReplacement(sb, replacementString);
        }

        // append the remaining part of the input string after the last match
        m.appendTail(sb);
        System.out.println("Mustace replacement is : " + sb.toString());


    }
}