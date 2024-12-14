package com.frapee;

import java.util.List;
import java.util.function.Consumer;

public class FoundationCallBack {

    private CallBackInterface callback;

    /**
     * Assigns the call back interface implementation to the class to be used
     * @param callback
     */
    FoundationCallBack(CallBackInterface callback) {
        this.callback = callback;
    }

    /**
     * Running a standard call back function interface
     * @return list of generated integers
     */
    public List<Integer> runCallBack() {
        return callback.generateList(1, 10);
    }

    /**
     * Consumer interface for call back see Consumer documentation
     * @param initialValue - integer number to start with
     * @param callback - consumber call back function
     */
    public void getNumbers(int initialValue, Consumer<Integer> callback) {
        callback.accept(initialValue);
    }

    /**
     * Consumer interface for adding values
     * @param initialValue - integer number to start with
     * @param addValue - integer being added
     * @param callback - consumber call back function
     */
    public void addNumbers(int initialValue, int addValue, Consumer<Integer> callback) {
        int addition = initialValue + addValue;
        callback.accept(addition);
    }

}
