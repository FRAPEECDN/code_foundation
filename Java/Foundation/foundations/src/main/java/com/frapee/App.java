package com.frapee;

public final class App {
    private App() {
    }

    /**
     * Basic Data Type explenation
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        boolean b = false;

        byte by = 10;
        short s = (short) Byte.MAX_VALUE + 1;
        int i = (int) Short.MAX_VALUE + 1;
        long l = (long) Integer.MAX_VALUE + 1;

        float f = 343.12f;
        double d = 35438580.21473;

        char ch = 'a';
        String aStr = "basic string value";
        
        // boxing
        Boolean bBox = b;
        Byte byBox = by;        
        Short sBox = s;
        Integer iBox = i;
        Long lBox = l;
        Float fBox = f;
        Double dBox = d;
        Character chBox = ch;           

        System.out.println("Basic data types");
        System.out.println("~~~~~~~~~~~~~~~~");
        System.out.println("Integers");
        System.out.println("~~~~~~~~~~~~~~~~");
        System.out.println("Limit functions example (int type)");
        System.out.print("Minimum value for int: " + Integer.MIN_VALUE);
        System.out.println();
        System.out.print("Maximum value for int: " + Integer.MAX_VALUE);
        System.out.println();
        System.out.print("Int Hex function: " + Integer.toHexString(65));
        System.out.println();
        System.out.print("signed byte (1): " + by);
        System.out.println(" - boxed " + byBox);
        System.out.print("signed short (2): " + s);
        System.out.println(" - boxed " + sBox);
        System.out.print("signed int types (4): " + i);
        System.out.println(" - boxed " + iBox);
        System.out.print("signed long (8): " + l);
        System.out.println(" - boxed " + lBox);
        System.out.println("~~~~~~~~~~~~~~~~");
        System.out.println("Floating points");
        System.out.println("~~~~~~~~~~~~~~~~");
        System.out.println();
        System.out.print("float: " + f);
        System.out.println(" - boxed " + fBox);
        System.out.print("double: " + d);
        System.out.println(" - boxed " + dBox);
        System.out.print("Minimum value for double: " + Double.MIN_VALUE);
        System.out.print(" Exponent :" + Double.MIN_EXPONENT);
        System.out.println();
        System.out.print("Maximum value for double: " + Double.MAX_VALUE);
        System.out.print(" Exponent :" + Double.MAX_EXPONENT);
        System.out.println();
        System.out.println("Is double infinite support: " + Double.POSITIVE_INFINITY);
        System.out.println("~~~~~~~~~~~~~~~~");
        System.out.println("String and characters");
        System.out.print("Basic character: " + ch);
        System.out.println(" - boxed " + chBox);
        System.out.println("Basic string: " + aStr);
        System.out.println("~~~~~~~~~~~~~~~~");
        System.out.println("Boolean");
        System.out.print("boolean example: " + b);
        System.out.println(" - boxed " + bBox);
        System.out.println();  
        System.out.println("~~~~~~~~~~~~~~~~");
    }
}
