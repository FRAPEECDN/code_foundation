#pragma once

#include <string>

/**
 * Foundation, basic example of what C++ internally can do.
 * Is used for function, control and repeat examples (Know it is very basic)
 */
class FoundationBasics
{
public:

	// Function examples
	void passByValye(int input);
	void passByReference(int& inAndOutput);
	int  returnValue(int input);

	// if examples
	int ifOnly(int left, int right);
	int ifAndElse(int left, int right);
	int ifMulti(int left, int right);

	// Switch examples
	int switchInteger(int option);
	//int switchString(std::string option);

	// Loop examples
	int* whileLoop(const int noLoops);
	int*  repeatLoop(const int noLoops);

	/*
	* C++ Control statement for loop setup example.
	* Note the template usage to use the size of the array 
	* Full function implemented in header file for compiler to figure out linking 
	* Will use reference as parameter for returning the modified array
	*/
	template <size_t N> void forLoop(const int noLoops, int(&rArray)[N]) {
		for (int iterateValue = 0; iterateValue < noLoops; iterateValue++) {
			rArray[iterateValue] = iterateValue * -1;
		}
	}

	/*
	* C++ Control statement for the new for each loop, notice the reference being given for the array
	* Note the template usage to use the size of the array 
	* Full function implemented in header file for compiler to figure out linking 
	* in order for the for each to work correctly
	*/
	template <size_t N> int forEachLoop(int(&fArray)[N]) {
		int idx = 0;
		for (int value : fArray) {
			value = value * -1;
			idx++;
		}
		return idx;
	}


private:

	static const int CHANGE_VAL = 2;

	constexpr unsigned long long hashstr(const char* s, size_t index = 0) {
		return s + index == nullptr || s[index] == '\0' ? 55 : hashstr(s, index + 1) * 33 + (unsigned char)(s[index]);
	}

};

