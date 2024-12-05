package com.frapee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        // Inputs - Processing (note exception handling with the catch)
        BufferedReader bfn = new BufferedReader(new InputStreamReader(System.in));

        int intInput = 0;
        do {
            try {
                System.out.println("Please provide a integer");
                intInput = Integer.parseInt(bfn.readLine());
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error");
            }
        } while (intInput == 0);

        System.out.println("Provided the integer :" + intInput);
        Pattern pattern = Pattern.compile("^[a-z]+$");
        boolean result = false;
        String strInput = "";
        do {
            try {
                System.out.println("Please provide a small character string");
                strInput = bfn.readLine();
                result = pattern.matcher(strInput).find();
            } catch (IOException e) {
                System.out.println("Error");
            }
        } while (!result);
        System.out.println("Provided the string :" + strInput);        
    }
    
}