package com.frapee;

import java.util.Arrays;

/**
 * Foundation usage of arrays, example is done using primitive int.
 * Binary, Linear and sorting is implemented as foundation
 */
public class FoundationArray {

    /**
     * Binary search implementation.
     * @param sArray - sorted array
     * @param target - target value
     * @return postion of target, -1 if not found.
     */
    public int findSortedArrayTarget(int[] sArray, int target) {
        int left = 0;
        int right = sArray.length - 1;

        while(left <= right) {
            int middle = left + (right - left) / 2;

            if (sArray[middle] == target) {
                return middle;
            } 
            
            if (sArray[middle] < target) {
                left = middle + 1;
            } else {
                right = middle -1;
            }
        }

        return -1; // aka not found
    }

    /**
     * Simple linear search for an unsorted array.
     * @param uArray - unsorted array
     * @param target - target value
     * @return postion of target, -1 if not found.
     */
    public int findUnSortedArrayTarget(int[] uArray, int target) {
        for (int i = 0; i < uArray.length; i++) {
            if (uArray[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /*
     * Quicksort implementation for arrays, not that it has 3 functions working together
     */

    /**
     * Private function that swap elements in array
     * @param tArray - array with elements
     * @param x - position to swab
     * @param y - other position to swab
     */
    private static void swap(int[] tArray, int x, int y) {
        int temp = tArray[x];
        tArray[x] = tArray[y];
        tArray[y] = temp;
    }

    /**
     * Private function that process a partition array between a left and right position using pivot point.
     * @param pArray array being partitioned
     * @param left - left index
     * @param right - right index
     * @return postion of pivot after sorting the partition
     */
    private static int partitionPivot(int[] pArray, int left, int right) {
        int pivotValue = pArray[right]; // choice pivot point

        int idx = left - 1; // Represents the last sorted known position
        // use right - 1 since pivot is rightmost value
        for (int i = left; i <= right - 1; i++) {
            if (pArray[i] < pivotValue) {
                idx++;
                swap(pArray, idx, i);
            }
        }
        swap(pArray, idx + 1, right);
        return idx + 1;
    }

    /**
     * Quick sort algorithm implementation. Take unsorted array and sort it.
     * Function is recursively being called to complete.
     * @param unsortedArray array that is unsorted or partition of the array that remains unsorted.
     * @param left - left index for sort to start
     * @param right - right index for sort to start
     */
    private static void quickSort(int[] unsortedArray, int left, int right) {
        
        if (left < right) {
            int pivot = partitionPivot(unsortedArray, left, right);
            quickSort(unsortedArray, left, pivot - 1);
            quickSort(unsortedArray, pivot + 1, right);
        }

    }

    /**
     * Sort unsorted array using Quick Sort method.
     * @param unsortedArray - the unsorted array
     */
    public void sortArray(int[] unsortedArray) {
        quickSort(unsortedArray, 0, unsortedArray.length - 1);
    }

    /**
     * Foundation function to merge pre-sorted arrays using 2 indexes for each array.
     * @param lArray left array
     * @param rArray right array
     * @return merged array
     */
    public int[] mergeSortedArrays(int[] lArray, int[] rArray) {

        int[] retArray = new int[lArray.length + rArray.length];

        int l = 0;
        int r = 0;
        for (int i = 0; i < retArray.length; i++) {
            if (r >= rArray.length) {
                retArray[i] = lArray[l];
                l++;
            } else if (l >= lArray.length) {
                retArray[i] = rArray[r];
                r++;
            } else {
                if (lArray[l] < rArray[r]) {
                    retArray[i] = lArray[l];
                    l++;
                } else {
                    retArray[i] = rArray[r];
                    r++;
                }
            }
        }

        return retArray;
    }

    /**
     * Foundational function to merge and then sort array using own merging and sorting implementations.
     * @param lArray left array
     * @param rArray right array
     * @return merged and sorted array
     */
    public int[] mergeAndSortArrays(int[] lArray, int[] rArray) {

        int[] retArray = new int[lArray.length + rArray.length];
        int ndx = 0;
        for (int i = 0; i < lArray.length; i++) {
            if (ndx >= retArray.length) {
                break;
            }
            retArray[ndx] = lArray[i];
            ndx++;
        }
        for (int i = 0; i < rArray.length; i++) {
            if (ndx >= retArray.length) {
                break;
            }
            retArray[ndx] = rArray[i];
            ndx++;
        }
        quickSort(retArray, 0, retArray.length - 1);
        return retArray;
    }

    /**
     * Foundational function to merge and then sort array only using existing functionality of Java.
     * @param lArray left array
     * @param rArray right array
     * @return merged and sorted array
     */    
    public int[] mergeAndSortUsingExisting(int[] lArray, int[] rArray) {
        int[] retArray = new int[lArray.length + rArray.length];
        System.arraycopy(lArray, 0, retArray, 0, lArray.length);
        System.arraycopy(rArray, 0, retArray, lArray.length, rArray.length);
        Arrays.sort(retArray);
        return retArray;
    }

    /**
     * Example for Array using stream to check if array contains even numbers
     * @param bArray - array being processed
     * @return indicator if all elements are even
     */
    public boolean predicateArrayFindEven(int[] bArray) {

        return Arrays.stream(bArray)
            .allMatch(x-> x % 2 == 0);

    }

    /**
     * Example for Array using stream to filter the array to return only even numbers
     * @param bArray - array being processed
     * @return array containing even numbers
     */
    public int[] arrayStreamFilter(int[] bArray) {
        return Arrays.stream(bArray)
            .filter(x -> x % 2 == 0)
            .toArray();
    }

}
