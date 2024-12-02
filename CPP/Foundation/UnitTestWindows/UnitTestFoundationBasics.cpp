#include "pch.h"
#include "CppUnitTest.h"
#include "../FoundationApplication/FoundationBasics.h"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace UnitTestWindows
{
	TEST_CLASS(UnitTestFoundationBasics)
	{
	public:

		TEST_METHOD_INITIALIZE(createBasics)
		{
			fb = new FoundationBasics();
		}

		TEST_METHOD_CLEANUP(cleanUpBasics)
		{
			delete fb;
			fb = nullptr;
		}
		
		TEST_METHOD(testFunctions) {
			int expected = 20;
			int value = 20;
			fb->passByValye(value);
			Assert::AreEqual(value, expected);
			expected = 40;
			fb->passByReference(value);
			Assert::AreEqual(value, expected);
			int result = fb->returnValue(value);
			expected = 80;
			Assert::AreEqual(result, expected);
		}

		TEST_METHOD(testIfs) {
			int valueA = 10;
			int valueB = 20;

			// Only if tests
			int result = fb->ifOnly(valueA, valueB);
			Assert::AreEqual(result, -1);
			result = fb->ifOnly(valueB, valueA);
			Assert::AreEqual(result, 0);

			// Test if/else
			result = fb->ifAndElse(valueA, valueB);
			Assert::AreEqual(result, -1);
			result = fb->ifAndElse(valueB, valueA);
			Assert::AreEqual(result, 1);

			valueA = 5;
			valueB = 15;
			result = fb->ifMulti(valueA, valueB);
			Assert::AreEqual(result, -1);

			valueA = 15;
			valueB = 5;
			result = fb->ifMulti(valueA, valueB);
			Assert::AreEqual(result, 1);

			valueA = 5;
			valueB = 5;
			result = fb->ifMulti(valueA, valueB);
			Assert::AreEqual(result, 0);
		}

		TEST_METHOD(testSwitchInteger) {
			for (int input = 0; input < 5; input++) {
				int resultO = fb->switchInteger(input);
				Assert::AreEqual(resultO, (input == 0 ? 0 : (input == 1) ? 1 : -1));
			}
		}

		//TEST_METHOD(testSwitchString) {
		//	std::string inputString = "ABCDEF";
		//	for (int i = 0; i < 5; i++) {
		//		std::string input = inputString.substr(i, i + 1);
		//		int resultO = fb->switchString(input);
		//		Assert::AreEqual(resultO, (input == "A") ? 0 : (input == "B") ? 1 : -1);
		//	}
		//}

		TEST_METHOD(testLoops) {
			int expectedNo = 10;
			int expectedArray[] = {0, -1, -2, -3, -4, -5, -6, -7, -8, -9};
			int *result1 = nullptr, *result2 = nullptr;

			int result3[10] = { 0 };

			result1 = fb->whileLoop(10);
			compareArrays(result1, expectedArray);
			result2 = fb->repeatLoop(10);
			compareArrays(result2, expectedArray);


			fb->forLoop(10, result3);
			compareArrays(result3, expectedArray);

			int setupArray[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
			int result4 = fb->forEachLoop(setupArray);
			Assert::AreEqual(result4, expectedNo);
			delete [] result1;
			delete [] result2;
		}

	private:

		FoundationBasics* fb;

		void compareArrays(int a[], int b[]) {
			int size = sizeof(a) / sizeof(a[0]);
			for (int i = 0; i < size; i++) {
				Assert::AreEqual(a[i], b[i]);
			}
		}

	};
}
