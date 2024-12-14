package com.frapee;

import java.util.List;

/**
 * Call back interface being declared, will be used to provide the callback functionality in Java
 * since Java does not have 'function pointers'
 */
public interface CallBackInterface {

    /**
     * Call back function for generating a list of numbers
     * @param start - start at number
     * @param stop - end at number
     * @return list containing the numbers
     */
    public List<Integer> generateList(int start, int stop);

}
