/*
* Program that uses concsole input and output
* Varification is done using fail and eof
* example of try and catch for error handling
*/

#include <iostream>
#include <string>

int main() {
    // Input test (looking directly at stream) - Requires integer input
    std::string theInput;
    int inputAsInt;

    std::cout << "Please provide a number" << std::endl;
    std::getline(std::cin, theInput);

    while (std::cin.fail() || std::cin.eof() || theInput.find_first_not_of("0123456789") != std::string::npos) {

        std::cout << "Error" << std::endl;

        if (theInput.find_first_not_of("0123456789") == std::string::npos) {
            std::cin.clear();
            std::cin.ignore(256, '\n');
        }

        std::getline(std::cin, theInput);
    }

    std::string::size_type st;
    inputAsInt = std::stoi(theInput, &st);
    std::cout << "number provided is " << inputAsInt << std::endl;

    // Input test requires small alphabetical characters
    std::cout << "Please provide a string (in lower case)" << std::endl;
    std::getline(std::cin, theInput);
    while (std::cin.fail() || std::cin.eof() || theInput.find_first_not_of("abcdefghijiklmnopqrstuvwxyz") != std::string::npos) {

        std::cout << "Error" << std::endl;

        if (theInput.find_first_not_of("abcdefghijiklmnopqrstuvwxyz") == std::string::npos) {
            std::cin.clear();
            std::cin.ignore(256, '\n');
        }

        std::getline(std::cin, theInput);
    }
    std::cout << "string provided is " << theInput << std::endl;

    return 0;
}