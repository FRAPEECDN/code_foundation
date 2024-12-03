// FoundationApplication.cpp : This file contains the 'main' function. Program execution begins and ends there.
//
#include <iostream>
#include <limits> 
#include <math.h>
#include <string>

int main()
{
    bool b = false;

    unsigned char uc = 10;
    char c = 10;
    // Commented code can work on some compilers but not others
    // unsigned short us = (unsigned short)std::numeric_limits<unsigned char>::max + 1;
    unsigned short us = 256;
    // short s = (short)std::numeric_limits<char>::max + 1;
    short s = 128;
    // unsigned int ui = (unsigned int)std::numeric_limits<unsigned short>::max + 1;
    unsigned int ui = 65536;
    // int i = ((int)std::numeric_limits<short>::max) + 1;
    int i = 32768;
    unsigned long ul = 32789;
    long l = 738347;
    // unsigned long ul = (unsigned long)std::numeric_limits<unsigned int>::max + 1;
    unsigned long long ull = 4294967296ULL;
    // long l = (long)std::numeric_limits<int>::max + 1;
    long long ll = 2147483648L;

    float f = 343.12f;
    double d = 35438580.21473;

    char character = 'a';
    std::string bString = "basic string value";
    std::wstring bwString = L"basic string in wide character";

    // Basic data tests (supported in c/c++)
    std::cout << "Basic data types" << std::endl;
    std::cout << "~~~~~~~~~~~~~~~~" << std::endl;
    std::cout << "Integers" << std::endl;
    std::cout << "~~~~~~~~~~~~~~~~" << std::endl;
    std::cout << "Limit functions example (int type)" << std::endl;
    std::cout << "Minimum value for int: " << std::numeric_limits<int>::min() << std::endl;
    std::cout << "Maximum value for int: " << std::numeric_limits<int>::max() << std::endl;
    std::cout << "Is int signed: " << std::numeric_limits<int>::is_signed << std::endl;
    std::cout << "unsigned byte (1): " << static_cast<unsigned>(uc) << std::endl;
    std::cout << "signed byte (1): " << static_cast<signed>(c) << std::endl;
    std::cout << "unsigned short (2): " << us << std::endl;
    std::cout << "signed short (2): " << s << std::endl;
    std::cout << "unsigned int types (4): " << ui << std::endl;
    std::cout << "signed int types (4): " << i << std::endl;
    std::cout << "unsigned long (4): " << ul << std::endl;
    std::cout << "signed long (4): " << l << std::endl;
    std::cout << "unsigned long long (8): " << ull << std::endl;
    std::cout << "signed long long (8): " << ll << std::endl;
    std::cout << "~~~~~~~~~~~~~~~~" << std::endl;
    std::cout << "Floating points" << std::endl;
    std::cout << "~~~~~~~~~~~~~~~~" << std::endl;
    std::cout << "float: " << f << std::endl;
    std::cout << "double: " << d << std::endl;
    std::cout << "Minimum value for double: " << std::numeric_limits<double>::min() << std::endl;
    std::cout << "Maximum value for bouble: " << std::numeric_limits<double>::max() << std::endl;
    std::cout << "Is double signed: " << std::numeric_limits<double>::is_signed << std::endl;
    std::cout << "Is double infinite support: " << std::numeric_limits<double>::has_infinity << std::endl;
    std::cout << "~~~~~~~~~~~~~~~~" << std::endl;
    std::cout << "String and characters" << std::endl;
    std::cout << "Basic character: " << character << std::endl;
    std::cout << "Basic string: " << bString << std::endl;
    std::wcout << "Wide string: " << bwString << std::endl;
    std::cout << "~~~~~~~~~~~~~~~~" << std::endl;
    std::cout << "Boolean" << std::endl;
    std::cout << "boolean example: " << b << std::endl;
    std::cout << "~~~~~~~~~~~~~~~~" << std::endl;
}
