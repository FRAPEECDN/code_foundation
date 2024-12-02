#pragma once
#include <array>
#include <algorithm>

class FoundationArray
{
public:

	void sortArray(int unsortedArray[], int size);
	int* mergeSortedArrays(int lArray[], int lSize, int rArray[], int rSize);
	int* mergeAndSortArrays(int lArray[], int lSize, int rArray[], int rSize);

	/*
	* Using algorithm to implement a predicate find_if
	* Note similar functions are find, find_if, find_if_not, find_end, find_first_of, search, search_n, adjacent_find
	*/
	template <typename Type, std::size_t size>
	bool findElement(const std::array<Type, size> sArray) {
		auto it = find_if(sArray.begin(), sArray.end(), [](int number) {return number % 2 == 0; });
		return (it != sArray.end());
	}

	/*
	* Using  algorithm to implement a filter function using find_if, it creates a array containing even numbers
	* it is not really efficient
	*/
	template <typename Type, std::size_t size>
	std::array<Type, size>& filterArray(const std::array<Type, size> sArray) {
		static std::array<Type, size> retArray;
		int retIdx = 0;
		auto it = find_if(sArray.begin(), sArray.end(), [](int number) {return number % 2 == 0; });
		while (it != sArray.end()) {
			retArray[retIdx] = *it;
			it++;
			retIdx++;
			it = find_if(it, sArray.end(), [](int number) {return number % 2 == 0; });
		}
		return retArray;
	}

	/*
	* Using std to implement merge and sort using STL
	*/
	template <typename Type, std::size_t... sizes>
	auto mergeAndSortUsingExisting(const std::array<Type, sizes>&... arrays)
	{
		std::array<Type, (sizes + ...)> result;
		std::size_t index{};

		((std::copy_n(arrays.begin(), sizes, result.begin() + index), index += sizes), ...);
		std::sort(result.begin(), result.end());

		return result;
	}

	int findSortedArrayTarget(int sArray[], int size, int target);
	int findUnSortedArrayTarget(int uArray[], int size, int target);
	static void quickSort(int unsortedArray[], int size, int left, int right);

private:
	static int partitionPivot(int pArray[], int size, int left, int right);
	static void swap(int tArray[], int size, int x, int y);


};

