#include "FoundationArray.h"
#include <algorithm>

/*
* C++ function that uses binary search implementation
*/
int FoundationArray::findSortedArrayTarget(int sArray[], int size, int target) {
	int left = 0, right = size;

	while (left <= right) {
		int mid = left + (right - left) / 2;

		if (sArray[mid] == target) {
			return mid;
		}

		if (sArray[mid] < target) {
			left = mid + 1;
		}
		else {
			right = mid - 1;
		}
	}

	return -1;
}

/*
* C++ Function that uses linear serach on unsorted array
*/
int FoundationArray::findUnSortedArrayTarget(int uArray[], int size, int target) {

	for (int i = 0; i < size; i++) {
		if (uArray[i] == target) {
			return i;
		}
	}

	return -1;
}

/*
* Quicksort with recursive calling to sort an array
*/
void FoundationArray::quickSort(int unsortedArray[], int size, int left, int right) {
	if (left < right) {
		int pivot = partitionPivot(unsortedArray, size, left, right);
		quickSort(unsortedArray, size, left, pivot - 1);
		quickSort(unsortedArray, size, pivot + 1, right);
	}
}

/*
* Calculate new pivot point, and then partitians the array based on pivot
*/
int FoundationArray::partitionPivot(int pArray[], int size, int left, int right) {
	int pivotValue = pArray[right]; // choice pivot point
	int idx = left - 1; // Represents the last sorted known position

	for (int i = left; i <= right - 1; i++) {
		if (pArray[i] < pivotValue) {
			idx++;
			swap(pArray, size, idx, i);
		}
	}

	swap(pArray, size, idx + 1, right);
	return idx + 1;
}

/*
* C++ Function that swaps position inside an array
*/
void FoundationArray::swap(int tArray[], int size, int x, int y) {
	int swap = tArray[x];
	tArray[x] = tArray[y];
	tArray[y] = swap;
}

/*
* C++ Function to call sorting on the unsorted array
*/
void FoundationArray::sortArray(int unsortedArray[], int size) {
	quickSort(unsortedArray, size, 0, size - 1);
}

/*
* C++ Function that works through the arrays determining which values comes next,
* it does require the arrays to be pre-sorted
*/
int* FoundationArray::mergeSortedArrays(int lArray[], int lSize, int rArray[], int rSize) {

	int size = lSize + rSize;
	int* retArray = new int[size]();

	int l = 0;
	int r = 0;
	for (int i = 0; i < size; i++) {
		if (r >= rSize) {
			retArray[i] = lArray[l];
			l++;
		}
		else if (l >= lSize) {
			retArray[i] = rArray[r];
			r++;
		}
		else {
			if (lArray[l] < rArray[r]) {
				retArray[i] = lArray[l];
				l++;
			}
			else {
				retArray[i] = rArray[r];
				r++;
			}
		}
	}

	return retArray;
}

/*
* C++ function to merge in both arrays, then run custom sorting function
*/
int* FoundationArray::mergeAndSortArrays(int lArray[], int lSize, int rArray[], int rSize) {

	int size = lSize + rSize;
	int ndx = 0;
	int* retArray = new int[size];
	for (int i = 0; i < lSize; i++) {
		if (ndx >= size) {
			break;
		}
		retArray[ndx] = lArray[i];
		ndx++;
	}
	for (int j = 0; j < rSize; j++) {
		if (ndx >= size) {
			break;
		}
		retArray[ndx] = rArray[j];
		ndx++;
	}
	quickSort(retArray, size, 0, size - 1);
	return retArray;
}
