package com.frapee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Main {

    static void handleDateTime() {
        LocalDate initialDate = LocalDate.parse("2007-05-10");
        System.out.println(initialDate);
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        LocalTime sixThirty = LocalTime.parse("06:30");
        System.out.println(sixThirty);
        LocalDateTime localDateTime = LocalDateTime.of(2015, Month.FEBRUARY, 20, 06, 30);
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        System.out.println(zonedDateTime);
    } 

    public static void main(String[] args) {
       long startTime = System.currentTimeMillis();
        handleDateTime();
        
        enumNumber value = enumNumber.INT_VALUE_ONE;
        System.out.println(value);
        switch (value) {
            case INT_VALUE_ONE:
                System.out.println("enum with value of one");
                break;
            default:
                System.out.println("enum with NOT a value of one");
        };
        
        enumString valueStr = enumString.STR_VALUE_FIRST;
        System.out.println(valueStr);
        switch (valueStr) {
            case STR_VALUE_FIRST:
                System.out.println("enum string with value of first");
                break;
            default:
                System.out.println("enum string with NOT a value of first");
        };
        
        Pojo simple = new Pojo();
        System.out.println(simple);
        simple.setNumber(100);
        simple.setName("Bob");
        
        System.out.print("get usage for simple :");
        System.out.println(simple.getNumber());
        
        Pojo init = new Pojo(3464,"Rob");
        System.out.println(init);

        ExampleRecord record1 = new ExampleRecord(1, "Nobody");
        ExampleRecord record2 = new ExampleRecord(2, "Dow");

        System.out.print("Record 1 :");
        System.out.println(record1);
        System.out.print("Record 2 :");
        System.out.println(record2);        

        long endTime = System.currentTimeMillis();
        System.out.print("Program took ");
        System.out.print(endTime - startTime);
        System.out.println(" miliseconds to execute");
    }
}