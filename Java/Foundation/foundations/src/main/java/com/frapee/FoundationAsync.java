package com.frapee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.javatuples.Pair;

/**
 * Class to call Async operations via pool->task, single thread, virtual thread and CompletableFuture
 * limited to what is available in Java
 * Also test up tuple return type Pair -> returning 2 values at same time
 */
public class FoundationAsync {

    // Create a thread pool to use for tasks
    private static final ExecutorService threadpool = Executors.newCachedThreadPool();

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

    /**
     * Running async as simple thread
     * @param number to process factorial of
     * @return
     */
    public long useThread(int number) {
        ThreadReturnable factorialThread = new ThreadReturnable();
        factorialThread.setNumber(number);
        Thread newThread = new Thread(factorialThread);
        newThread.start();
        try {
            newThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return factorialThread.getValue();
    }

    /**
     * Running async as simple virtual thread
     * @param number to process factorial of
     * @return
     */
    public long useVirtualThread(int number) {
        ThreadReturnable factorialThread = new ThreadReturnable();
        factorialThread.setNumber(number);

        Thread virtualThread = Thread.startVirtualThread(factorialThread);
        try {
            virtualThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return factorialThread.getValue();
    }    

    /**
     * Running async as CompletableFuture
     * @param number to process factorial of
     * @return Pair tuple containing count and result
     */
    public Pair<Long, Long> useCompletable(int number) {
        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> factorial(number));
        long counter = 0;
        while (!completableFuture.isDone()) { 
            counter++; 
        }
        long result = 0L;
        try {
            result = completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return new Pair<Long, Long>(counter, result);
    }

    /**
     * Running async as a Task from a thread pool
     * @param number to process factorial of
     * @return Pair tuple containing count and result
     */
    public Pair<Long, Long> useTask(int number) {
        Future<Long> futureTask = threadpool.submit(() -> factorial(number)); 
        long counter = 0;
        while (!futureTask.isDone()) { 
            counter++;
        }
        
        long result = 0;
        try {
            result = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new Pair<Long, Long>(counter, result);
    }

}
