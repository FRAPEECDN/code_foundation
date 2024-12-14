package com.frapee;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation class for callback interface
 */
public class CallBackImplementation implements CallBackInterface {

    @Override
    public List<Integer> generateList(int start, int stop) {
        List<Integer> returnList = new ArrayList<>();
        for (int i = start; i <= stop; i++ ) {
            returnList.add(i);
        }

        return returnList;
    }

}
