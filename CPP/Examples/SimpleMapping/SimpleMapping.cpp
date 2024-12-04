/*
* Example for handling mapping, using unordered map
* For an array of values of integers, record and then print the result of how many numbers was found
* For example array of (1, 4, 5, 1, 5, 7, 9, 0, 5)
* The result should be for 1 - 2 found, 5 - 3 found
*/
#include <iostream>
#include <unordered_map>

void printResults(std::unordered_map<int, int> result) {

    for (auto it = result.begin(); it != result.end(); it++) {

        std::cout << "Number " << it->first << " appeared " << it->second << std::endl;
    }

}

int main()
{
    std::cout << "Start" << std::endl;
    int input[] = { 1, 4, 5, 1, 5, 7, 9, 8, 5 };
    int size = sizeof(input) / sizeof(input[0]);
    std::unordered_map <int, int> map_array;

    for (int i = 0; i < size; i++) {
        int number = input[i];
        auto isThere = map_array.find(number);
        if (isThere == map_array.end()) {
            map_array.insert( { number, 1 } );
        }
        else {
            isThere->second += 1;
        }
    }

    printResults(map_array);
    std::cout << "End" << std::endl;
}
