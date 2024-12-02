#include "pch.h"
#include "../FoundationApplication/FoundationBasics.h"

// The fixture for testing class Foo.
class FoundationBasicTest : public testing::Test {
protected:
	// You can remove any or all of the following functions if their bodies would
	// be empty.

	FoundationBasicTest() {
		// You can do set-up work for each test here.
		fb = new FoundationBasics();
	}

	~FoundationBasicTest() override {
		// You can do clean-up work that doesn't throw exceptions here.
		delete fb;
		fb = nullptr;
	}

	// If the constructor and destructor are not enough for setting up
	// and cleaning up each test, you can define the following methods:

	void SetUp() override {
		// Code here will be called immediately after the constructor (right
		// before each test).
	}

	void TearDown() override {
		// Code here will be called immediately after each test (right
		// before the destructor).
	}

	void compareArrays(int a[], int b[]) {
		int size = sizeof(a) / sizeof(a[0]);
		for (int i = 0; i < size; i++) {
			EXPECT_EQ(a[i], b[i]);
		}
	}

	// Class members declared here can be used by all tests in the test suite
	FoundationBasics* fb;
};



TEST_F(FoundationBasicTest, TestName) {
  EXPECT_EQ(1, 1);
  EXPECT_TRUE(true);
}

TEST_F(FoundationBasicTest, testFunctions) {
	int expected = 20;
	int value = 20;
	fb->passByValye(value);
	EXPECT_EQ(value, expected);
	expected = 40;
	fb->passByReference(value);
	EXPECT_EQ(value, expected);
	int result = fb->returnValue(value);
	expected = 80;
	EXPECT_EQ(result, expected);
}

TEST_F(FoundationBasicTest, testIfs) {
	int valueA = 10;
	int valueB = 20;

	// Only if tests
	int result = fb->ifOnly(valueA, valueB);
	EXPECT_EQ(result, -1);
	result = fb->ifOnly(valueB, valueA);
	EXPECT_EQ(result, 0);

	// Test if/else
	result = fb->ifAndElse(valueA, valueB);
	EXPECT_EQ(result, -1);
	result = fb->ifAndElse(valueB, valueA);
	EXPECT_EQ(result, 1);

	valueA = 5;
	valueB = 15;
	result = fb->ifMulti(valueA, valueB);
	EXPECT_EQ(result, -1);

	valueA = 15;
	valueB = 5;
	result = fb->ifMulti(valueA, valueB);
	EXPECT_EQ(result, 1);

	valueA = 5;
	valueB = 5;
	result = fb->ifMulti(valueA, valueB);
	EXPECT_EQ(result, 0);
}

TEST_F(FoundationBasicTest, testSwitchInteger) {
	for (int input = 0; input < 5; input++) {
		int resultO = fb->switchInteger(input);
		EXPECT_EQ(resultO, (input == 0 ? 0 : (input == 1) ? 1 : -1));
	}
}

TEST_F(FoundationBasicTest, testLoops) {
	int expectedNo = 10;
	int expectedArray[] = { 0, -1, -2, -3, -4, -5, -6, -7, -8, -9 };
	int* result1 = nullptr, * result2 = nullptr;

	int result3[10] = { 0 };

	result1 = fb->whileLoop(10);
	compareArrays(result1, expectedArray);
	result2 = fb->repeatLoop(10);
	compareArrays(result2, expectedArray);


	fb->forLoop(10, result3);
	compareArrays(result3, expectedArray);

	int setupArray[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	int result4 = fb->forEachLoop(setupArray);
	EXPECT_EQ(result4, expectedNo);
	delete[] result1;
	delete[] result2;
}

int main(int argc, char** argv) {
	testing::InitGoogleTest(&argc, argv);
	return RUN_ALL_TESTS();
}