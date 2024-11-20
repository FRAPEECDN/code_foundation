package com.frapee;

/**
 * Foundation, basic example of what Java internally can do.
 * Is used for function, control and repeat examples (Know it is very basic)
 */
public class FoundationBasics {

    private static int CHANGE_VAL = 2;

    /**
     * Java only allows parameters to be pass by value.
     * expectation is caller value will not change
     * @param input - for pass by value test
     */
    public void passByValueOnly(int input) {
        input *= CHANGE_VAL;
    }

    /**
     * Java will modify returned value
     * @param input - pass by value input
     * @return modified value
     */
    public int returnChanged(int input) {
        return input * CHANGE_VAL;
    }

    /**
     * Java If statement example
     * @param left - value for comparison in if
     * @param right - value for comparison in if
     * @return test results
     */
    public int ifOnly(int left, int right) {
        if (left < right) {
            return -1;
        }
        return 0; // Note function required to return something
    }

    /**
     *  Java If else statement example
     * @param left - value for comparison in if
     * @param right - value for comparison in if
     * @return test results
     */
    public int ifAndElse(int left, int right) {
        if (left < right) {
            return -1; 
        } else {
            return 1;
        }
    }

    /**
     * Java If, Else If, Else statement example
     * @param left - value for comparison in if
     * @param right - value for comparison in if
     * @return test results
     */
    public int ifMulti(int left, int right) {
        if (left < right) {
            return -1;
        } else if (left > right) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Switch statement example (old style)
     * @param option - option on which switch will run
     * @return some value to test switch
     */
    public int switchOlder(int option) {
        int returnVal = 0;
        switch (option) {
            case 0:
                returnVal = 0;
                break;
            case 1:
                returnVal = 1;
                break;
            default:
                returnVal = -1;
                break;
        }
        return returnVal;
    }

    /**
     * Switch statement can handle Strings by default
     * @param option - option on which switch will run
     * @return some value to test switch
     */
    public int switchString(String option) {
        int returnVal = 0;
        switch (option) {
            case "A":
                returnVal = 0;
                break;
            case "B":
                returnVal = 1;
                break;
        
            default:
                returnVal = -1;
                break;
        }
        return returnVal;
    }

    /**
     * Switch statement example (Java 17 and later).
     * Can return directly on options
     * @param option - option on which switch will run
     * @return some value to test switch
     */
    public int switchNew(int option) {
        return switch (option) {
            case 0 -> 0;
            case 1 -> 1;
            default -> -1;
        };
    }

    /**
     * Control statement for while setup example. Initialization is required. Test is a start of loop.
     * @param noLoops number of loops being done
     * @return
     */
    public int[] whileLoop(int noLoops) {
        int[] retArray = new int[noLoops];
        int iterateValue = 0;
        while (iterateValue != noLoops) {
            retArray[iterateValue] = iterateValue * -1;
            iterateValue++;
        }
        return retArray;
    }

    /**
     * Control statement for repeat setup example. Test is at end of loop.
     * @param noLoops - number of repeats being done
     * @return
     */
    public int[] repeatLoop(int noLoops) {
        int[] retArray = new int[noLoops];
        int iterateValue = 0;
        do {
            retArray[iterateValue] = iterateValue * -1;
            iterateValue++;
        } while (iterateValue != noLoops);
        return retArray;
    }

    /**
     * Control statement for loop setup example.
     * @param noLoops - number of for loop repeat
     * @return
     */
    public int[] forLoop(int noLoops) {
        int[] retArray = new int[noLoops];
        for (int iterateValue = 0; iterateValue < noLoops; iterateValue++) {
            retArray[iterateValue] = iterateValue * -1;

        }
        return retArray;
    }

    /**
     * Control statement for for each loop.
     * @param fArray - array presenting what is collection that for executes on.
     * @return
     */
    public int[] forEachLoop(int[] fArray) {
        int[] retArray = new int[fArray.length];
        int idx = 0;
        for (int value : fArray) {
            retArray[idx] = value * -1;
            idx++;
        }
        return retArray;
    }

}
