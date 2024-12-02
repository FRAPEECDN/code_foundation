#include "FoundationBasics.h"

/*
* Example C++ functionality of function pass by value
*/
void FoundationBasics::passByValye(int input) {
	input = input * CHANGE_VAL;
}

/*
* Example C++ functionality of function pass by refernce
* Reference is specific alaias aka const pointer, so it can change
*/
void FoundationBasics::passByReference(int& input) {
	input = input * CHANGE_VAL;
}

/*
* Example of C++ return function
*/
int FoundationBasics::returnValue(int input) {
	return input * CHANGE_VAL;
}

/*
* C++ If statement example
*/
int FoundationBasics::ifOnly(int left, int right) {
    if (left < right) {
        return -1;
    }
    return 0; // Note function required to return something
}

/*
* C++ If Else statement example
*/
int FoundationBasics::ifAndElse(int left, int right) {
    if (left < right) {
        return -1;
    }
    else {
        return 1;
    }
}

/*
* C++ If Else If, Else statement example
*/
int FoundationBasics::ifMulti(int left, int right) {
    if (left < right) {
        return -1;
    }
    else if (left > right) {
        return 1;
    }
    else {
        return 0;
    }
}

/*
* C++ Switch example using Integer values
*/
int FoundationBasics::switchInteger(int option) {
    int returnVal = 0;
    switch (option) {
    case 0:
        returnVal = 0;
        break;
    case 1:
        returnVal = 1;
        break;
    default:
        returnVal = -1;
        break;
    }
    return returnVal;
}

/*
* C++ Switch - using hashing constexpr to do compile time, so hash can represent
* constant strings for the switch
*/
//int FoundationBasics::switchString(std::string option) { 
//    int returnVal = 0;
//    constexpr const char* aVal = "A";
//    constexpr const char* bVal = "B";
//    constexpr unsigned long long hashedA = hashstr("A");
//    constexpr unsigned long long hashedB = 1; // hashstr(bVal);
//    
//    switch (hashstr(option.c_str())) {
//    case hashedA:
//        returnVal = 0;
//        break;
//    case hashedB:
//        returnVal = 1;
//        break;
//    default:
//        returnVal = -1;
//        break;
//    }
//    return returnVal;
//}

/*
* C++ Control statement for while setup example. Initialization is required. Test is a start of loop.
* Returns point to new array created, needs to be deleted
*/
int* FoundationBasics::whileLoop(const int noLoops) {
    int* retArray = new int[noLoops]();
    int iterateValue = 0;
    while (iterateValue != noLoops) {
        retArray[iterateValue] = iterateValue * -1;
        iterateValue++;
    }
    return retArray;
}

/*
* C++ Control statement for repeat setup example. Test is at end of loop.
* For this will return a reference to the new array
*/
int* FoundationBasics::repeatLoop(const int noLoops) {
    int* retArray = new int[noLoops]();
    int iterateValue = 0;
    do {
        retArray[iterateValue] = iterateValue * -1;
        iterateValue++;
    } while (iterateValue != noLoops);
    return retArray;
}
