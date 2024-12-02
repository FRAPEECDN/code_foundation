#include "FoundationEncodingStrings.h"

std::string FoundationEncodingStrings::encodeToNumberLowerAlpha(std::string alphaString) {
    static std::string numberString;
    for (auto letter : alphaString) {
        if (letter == ' ') {
            if (!numberString.empty()) {
                numberString.pop_back();
            }
            numberString += '+';
        }
        else {
            int number = letter - 'a' + 1;
            numberString += std::to_string(number);
            numberString += ' ';
        }
    }
    if (!numberString.empty()) {
        numberString.pop_back();
    }
    return numberString;
}

std::string FoundationEncodingStrings::encodeToNumberUpperAlpha(std::string alphaString) {
    static std::string numberString;
    for (auto letter : alphaString) {
        if (letter == ' ') {
            if (!numberString.empty()) {
                numberString.pop_back();
            }
            numberString += '+';
        }
        else {
            int number = letter - 'A' + 1;
            numberString += std::to_string(number);
            numberString += ' ';
        }
    }
    if (!numberString.empty()) {
        numberString.pop_back();
    }
    return numberString;
}

std::string FoundationEncodingStrings::decodeToLowerAlphaNumber(std::string numberString) {
    static std::string letterString;
    std::string digits = "";
    for (auto digit : numberString) {
        if (digit == ' ' || digit == '+') {
            int number = std::stoi(digits) + 'a' - 1;
            char letter = char(number);
            letterString += letter;
            if (digit == '+') {
                letterString += ' ';
            }
            digits = "";
        }
        else {
            digits += digit;
        }
    }
    if (digits.length() > 0) {
        int number = std::stoi(digits) + 'a' - 1;
        char letter = char(number);
        letterString += letter;
    }
    return letterString;
}

std::string FoundationEncodingStrings::decodeToUpperAlphaNumber(std::string numberString) {
    static std::string letterString;
    std::string digits = "";
    for (auto digit : numberString) {
        if (digit == ' ' || digit == '+') {
            int number = std::stoi(digits) + 'A' - 1;
            char letter = char(number);
            letterString += letter;
            if (digit == '+') {
                letterString += ' ';
            }
            digits = "";
        }
        else {
            digits += digit;
        }
    }
    if (digits.length() > 0) {
        int number = std::stoi(digits) + 'A' - 1;
        char letter = char(number);
        letterString += letter;
    }
    return letterString;
}

std::string FoundationEncodingStrings::encodeAlphaNumeric(std::string alphaNumeric) {
    static std::string numberString;
    for (auto letter : alphaNumeric) {
        int number = letter - ' ' + 1;
        numberString += std::to_string(number);
        numberString += ' ';
    }
    if (!numberString.empty()) {
        numberString.pop_back();
    }
    return numberString;
}

std::string FoundationEncodingStrings::decodeAlphaNumeric(std::string numberString) {
    static std::string letterString;
    std::string digits = "";
    for (auto digit : numberString) {
        if (digit == ' ') {
            int number = std::stoi(digits) + ' ' - 1;
            char letter = char(number);
            letterString += letter;
            digits = "";
        }
        else {
            digits += digit;
        }
    }
    if (digits.length() > 0) {
        int number = std::stoi(digits) + ' ' - 1;
        char letter = char(number);
        letterString += letter;
    }
    return letterString;
}
