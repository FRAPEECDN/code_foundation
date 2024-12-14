package com.frapee;

public class ThreadReturnable implements Runnable {

    private volatile long value = 0;
    private long number = 0;

    /**
     * Factorial function with simple loop that will be run a sync
     * @param number input number being calculated
     * @return factorial for the number
     */
    private long factorial(long number) {
        long result = 1;
        for (long i = number; i > 0; i--) {
            result *= i;
        }
        return result;
    }

    @Override
    public void run() {
        value = factorial(this.number);
    }

    /**
     * @return calculated value inside the thread
     */
    public long getValue() {
        return value;
    }

    /**
     * @param number to run factorial on
     */
    public void setNumber(long number) {
        this.number = number;
    }

}
