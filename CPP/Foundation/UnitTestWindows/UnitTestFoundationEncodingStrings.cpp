#include "pch.h"
#include "CppUnitTest.h"
#include "../FoundationApplication/FoundationEncodingStrings.h"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace UnitTestWindows
{
	TEST_CLASS(UnitTestFoundationEncodingStrings)
	{
	public:

		TEST_METHOD(testEncodingLower) {
			std::string testString = "abcdefghijklm nopqrstuvwxyz";
			std::string expectedString = "1 2 3 4 5 6 7 8 9 10 11 12 13+14 15 16 17 18 19 20 21 22 23 24 25 26";
			std::string actualString = FoundationEncodingStrings::encodeToNumberLowerAlpha(testString);
			Assert::AreEqual(actualString, expectedString);
		}

		TEST_METHOD(testDecodingLower) {
			std::string testString = "1 2 3 4 5 6 7 8 9 10 11 12 13+14 15 16 17 18 19 20 21 22 23 24 25 26";
			std::string expectedString = "abcdefghijklm nopqrstuvwxyz";
			std::string actualString = FoundationEncodingStrings::decodeToLowerAlphaNumber(testString);
			Assert::AreEqual(actualString, expectedString);
		}

		TEST_METHOD(testEncodingUpper) {
			std::string testString = "ABCDEFGHIJKLM NOPQRSTUVWXYZ";
			std::string expectedString = "1 2 3 4 5 6 7 8 9 10 11 12 13+14 15 16 17 18 19 20 21 22 23 24 25 26";
			std::string actualString = FoundationEncodingStrings::encodeToNumberUpperAlpha(testString);
			Assert::AreEqual(actualString, expectedString);
		}

		TEST_METHOD(testDecodingUpper) {
			std::string testString = "1 2 3 4 5 6 7 8 9 10 11 12 13+14 15 16 17 18 19 20 21 22 23 24 25 26";
			std::string expectedString = "ABCDEFGHIJKLM NOPQRSTUVWXYZ";
			std::string actualString = FoundationEncodingStrings::decodeToUpperAlphaNumber(testString);
			Assert::AreEqual(actualString, expectedString);
		}

		TEST_METHOD(testEncodeAlphaNumeric) {
			std::string testString = "AaBbCcDdEeFfGgHhIiJjKkLlMm []{}NnOoPpQqRrSsTtUuVvWwXxYyZz";
			std::string expectedString = "34 66 35 67 36 68 37 69 38 70 39 71 40 72 41 73 42 74 43 75 44 76 45 77 46 78 1 60 62 92 94 47 79 48 80 49 81 50 82 51 83 52 84 53 85 54 86 55 87 56 88 57 89 58 90 59 91";
			std::string actualString = FoundationEncodingStrings::encodeAlphaNumeric(testString);
			Assert::AreEqual(actualString, expectedString);
		}

		TEST_METHOD(testDecodeAlphaNumeric) {
			std::string testString = "34 66 35 67 36 68 37 69 38 70 39 71 40 72 41 73 42 74 43 75 44 76 45 77 46 78 1 60 62 92 94 47 79 48 80 49 81 50 82 51 83 52 84 53 85 54 86 55 87 56 88 57 89 58 90 59 91";
			std::string expectedString = "AaBbCcDdEeFfGgHhIiJjKkLlMm []{}NnOoPpQqRrSsTtUuVvWwXxYyZz";
			std::string actualString = FoundationEncodingStrings::decodeAlphaNumeric(testString);
			Assert::AreEqual(actualString, expectedString);
		}

	};
}
