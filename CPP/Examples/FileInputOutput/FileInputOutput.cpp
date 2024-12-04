// FileInputOutput.cpp : This file contains the 'main' function. Program execution begins and ends there.
//
#include <iostream>
#include <fstream>
#include <string>
#include <regex>
#include <map>

/*
* Process the line by mapping words found in the line
*/
void processLine(std::string line, std::map<std::string, long>& map) {

    std::regex word_regex("(\\w+)");
    auto words_begin = std::sregex_iterator(line.begin(), line.end(), word_regex);
    auto words_end = std::sregex_iterator();
    for (std::sregex_iterator i = words_begin; i != words_end; ++i)
    {
        std::smatch match = *i;
        std::string match_str = match.str();
        auto iterFound = map.find(match_str);
        if (iterFound != map.end()) {
            iterFound->second++;
        }
        else {
            map.emplace(match_str, 1);
        }
    }
}

// Process the file by reading text lines through the file and mapping them
void processFile(std::string filename, std::map<std::string, long>& map) {
    std::fstream fileReader;
    fileReader.open(filename, std::ios::in);
    if (fileReader.is_open()) {
        std::string line;
        while (std::getline(fileReader, line)) {
            processLine(line, map);
        }
    }
    else {
        std::cout << "File is invalid. Cannot open for reading." << std::endl;
    }
    fileReader.close();
}

void switchmapping(std::map<std::string, long>& map, std::map<long, std::vector<std::string>>& countMap) {
    for (auto it = map.begin(); it != map.end(); it++) {
        auto iterFound = countMap.find(it->second);
        if (iterFound != countMap.end()) {
            iterFound->second.push_back(it->first);
        }
        else {
            std::vector<std::string> newList;
            newList.push_back(it->first);
            countMap.emplace(it->second, newList);
        }
    }
}

void createFile(std::string filename, std::map<long, std::vector<std::string>>& countMap) {
    std::fstream fileWriter;
    fileWriter.open(filename, std::ios::out);
    if (fileWriter.is_open()) {
        for (auto it = countMap.begin(); it != countMap.end(); it++) {
            fileWriter << "Counted ";
            fileWriter << it->first;
            fileWriter << " the following words : ";
            for (auto itS = it->second.begin(); itS != it->second.end(); itS++) {
                fileWriter << *itS;
                fileWriter << ", ";
            }
            fileWriter << std::endl;
            fileWriter.flush();
        }
    }
    else {
        std::cout << "File is invalid. Cannot open for writing." << std::endl;
    }
    fileWriter.close();
}

int main()
{
    std::map<std::string, long> wordMapSmall;
    std::map<std::string, long> wordMapMedium;
    std::map<std::string, long> wordMapLarge;
    std::map<long, std::vector<std::string>> countMapSmall;
    std::map<long, std::vector<std::string>> countMapMedium;
    std::map<long, std::vector<std::string>> countMapLarge;

    std::cout << "File Processing" << std::endl;
    std::cout << "===============" << std::endl;
    std::cout << "Small File" << std::endl;
    processFile("../../../data/small_input.txt", wordMapSmall);
    switchmapping(wordMapSmall, countMapSmall);
    createFile("small_count_output.txt", countMapSmall);

    std::cout << "===============" << std::endl;
    std::cout << "Medium File" << std::endl;
    processFile("../../../data/medium_input.txt", wordMapMedium);
    switchmapping(wordMapMedium, countMapMedium);
    createFile("medium_count_output.txt", countMapMedium);

    std::cout << "===============" << std::endl;
    std::cout << "Large File" << std::endl;
    processFile("../../../data/large_input.txt", wordMapLarge);
    switchmapping(wordMapLarge, countMapLarge);
    createFile("large_count_output.txt", countMapLarge);

    std::cout << "File Processing Done" << std::endl;
}

