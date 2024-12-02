#include "pch.h"
#include "CppUnitTest.h"
#include <array>
#include "../FoundationApplication/FoundationArray.h"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace UnitTestWindows
{
	TEST_CLASS(UnitTestFoundationArray)
	{
	public:

		TEST_METHOD(testCustomFound) {
			FoundationArray fa;
			int sortedArray[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
			int size = sizeof(sortedArray) / sizeof(sortedArray[0]);
			int position = fa.findSortedArrayTarget(sortedArray, size, 3);
			Assert::AreEqual(position, 2);
			position = fa.findSortedArrayTarget(sortedArray, size, 5);
			Assert::AreEqual(position, 4);
			position = fa.findSortedArrayTarget(sortedArray, size, 7);
			Assert::AreEqual(position, 6);
		}

		TEST_METHOD(testCustomNotFound) {
			FoundationArray fa;
			int sortedArray[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
			int size = sizeof(sortedArray) / sizeof(sortedArray[0]);
			int position = fa.findSortedArrayTarget(sortedArray, size, 10);
			Assert::AreEqual(position, -1);
		}

		TEST_METHOD(testLinearFound) {
			FoundationArray fa;
			int sortedArray[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			int size = sizeof(sortedArray) / sizeof(sortedArray[0]);
			int position = fa.findUnSortedArrayTarget(sortedArray, size, 3);
			Assert::AreEqual(position, 2);
			position = fa.findUnSortedArrayTarget(sortedArray, size, 5);
			Assert::AreEqual(position, 4);
			position = fa.findUnSortedArrayTarget(sortedArray, size, 7);
			Assert::AreEqual(position, 6);
		}

		TEST_METHOD(testLinearNotFound) {
			FoundationArray fa;
			int sortedArray[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			int size = sizeof(sortedArray) / sizeof(sortedArray[0]);
			int position = fa.findUnSortedArrayTarget(sortedArray, size, 10);
			Assert::AreEqual(position, -1);
		}

		TEST_METHOD(testCustomSort) {
			FoundationArray fa;
			int unsortedArray[] = {2, 9, 4, 7, 3, 6, 5, 8, 1};
			int size = sizeof(unsortedArray) / sizeof(unsortedArray[0]);
			int sortedArray[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
			fa.sortArray(unsortedArray, size);
			compareArrays(unsortedArray, sortedArray, size);
		}

		TEST_METHOD(testSortedMerge) {
			FoundationArray fa;
			int leftArray[] = {3, 5, 7, 9};
			int sizeLeft = sizeof(leftArray) / sizeof(leftArray[0]);
			int rightArray[] = {1, 2, 4, 6, 8};
			int sizeRight = sizeof(rightArray) / sizeof(rightArray[0]);
			int expected[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
			int size = sizeof(expected) / sizeof(expected[0]);

			int* result = fa.mergeSortedArrays(leftArray, sizeLeft, rightArray, sizeRight);
			compareArrays(result, expected, size);
			delete[] result;
		}

		TEST_METHOD(testUnsortedArrayMerge) {
			FoundationArray fa;
			int leftArray[] = { 8, 6, 4, 9 };
			int sizeLeft = sizeof(leftArray) / sizeof(leftArray[0]);
			int rightArray[] = {7, 3, 5, 2, 1};
			int sizeRight = sizeof(rightArray) / sizeof(rightArray[0]);
			int expected[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			int size = sizeof(expected) / sizeof(expected[0]);

			int* result = fa.mergeAndSortArrays(leftArray, sizeLeft, rightArray, sizeRight);
			compareArrays(result, expected, size);
			delete[] result;
		}

		TEST_METHOD(testArrayMergeExistingMethods) {
			FoundationArray fa;
			const std::array<int, 4> leftArray = { 8, 6, 4, 9 };
			const std::array<int, 5> rightArray = { 7, 3, 5, 2, 1 };
			const std::array<int, 9> expected = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

			std::array<int, 9> result = fa.mergeAndSortUsingExisting(leftArray, rightArray);
			compareArrays(result, expected);
		}

		TEST_METHOD(testExistingBinarySortFound) {
			const std::array<int, 9> searchArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			bool result = std::binary_search(searchArray.begin(), searchArray.end(), 3);
			auto iter = std::lower_bound(searchArray.begin(), searchArray.end(), 3);
			Assert::IsTrue(result); 
			int position = iter - searchArray.begin();
			Assert::AreEqual(position, 2);
			result = std::binary_search(searchArray.begin(), searchArray.end(), 5);
			iter = std::lower_bound(searchArray.begin(), searchArray.end(), 5);
			position = iter - searchArray.begin();
			Assert::IsTrue(result); 
			Assert::AreEqual(position, 4);
			result = std::binary_search(searchArray.begin(), searchArray.end(), 7);
			iter = std::lower_bound(searchArray.begin(), searchArray.end(), 7);
			position = iter - searchArray.begin();
			Assert::IsTrue(result); 
			Assert::AreEqual(position, 6);
		}

		TEST_METHOD(testExistingBinarySortNotFound) {
			const std::array<int, 9> searchArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			bool result = std::binary_search(searchArray.begin(), searchArray.end(), 10);
			Assert::IsFalse(result);
		}

		TEST_METHOD(testFindAlgorithm) {
			FoundationArray fa;
			const std::array<int, 5> matchingArray = { 2, 4, 6, 8, 10 };
			const std::array<int, 5> noMatchingArray = { 1, 3, 5, 7, 9 };

			Assert::IsTrue(fa.findElement(matchingArray));
			Assert::IsFalse(fa.findElement(noMatchingArray));
		}

		TEST_METHOD(testFilterAlgorithm) {
			FoundationArray fa;
			const std::array<int, 10> lookupArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
			const std::array<int, 10> expectedArray = { 2, 4, 6, 8, 10, 0, 0, 0, 0, 0 };
			std::array<int, 10> resultArray = fa.filterArray(lookupArray);
			compareArrays(resultArray, expectedArray);
		}

	private:

		void compareArrays(int a[], int b[], int size) {
			for (int i = 0; i < size; i++) {
				Assert::AreEqual(a[i], b[i]);
			}
		}

		template <typename Type, std::size_t size>
		void compareArrays(const std::array<Type, size> result, const std::array<Type, size> expected) {
			Assert::AreEqual(result.size(), expected.size());
			for (int i = 0; i < result.size(); i++) {
				Assert::AreEqual(result[i], expected[i]);
			}
		}

	};
}
