#pragma once
#include <string>

class FoundationEncodingStrings
{

public:
	static std::string encodeToNumberLowerAlpha(std::string alphaString);
	static std::string encodeToNumberUpperAlpha(std::string alphaString);
	static std::string decodeToLowerAlphaNumber(std::string numberString);
	static std::string decodeToUpperAlphaNumber(std::string numberString);
	static std::string encodeAlphaNumeric(std::string alphaNumeric);
	static std::string decodeAlphaNumeric(std::string numberString);

};

