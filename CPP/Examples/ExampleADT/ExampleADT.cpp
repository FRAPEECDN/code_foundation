// C++
// Struct, Union, Enum and Simple Class (Plain Old CPP - :) )
// Show how Date and Time is handled in modern why
#include <iostream>
#include <string>
#include <chrono>

#include <ctime>
#include <iomanip>

class SimpleCPP {
private:
    int number;
    std::string name;

public:
    // constructors
    SimpleCPP() {
        this->number = 0;
        this->name = "";
    }

    SimpleCPP(int number, std::string name) {
        this->number = number;
        this->name = name;
    }

    // Getters    
    int getNumber() {
        return this->number;
    }

    std::string getName() {
        return this->name;
    }

    // Setters
    void setNumber(int no) {
        this->number = no;
    }

    void setName(std::string name) {
        this->name = name;
    }

};

enum class Example_enum {
    VALUE_ONE,
    VALUE_TWO,
    VALUE_THREE,
    VALUE_FOUR,
    VALUE_FIVE,
};

struct Example_struct {
    int number;
    std::string name;

    Example_struct() {
        number = 0;
        name = "";
    }
};

union Example_union {
    int numberI;
    double numberD;
};

void handleDateTime() {
    //// Create a time via structure using STL (not fully supported by MSVC)
    //std::tm tm{};
    //tm.tm_year = 2022 - 1900;
    //tm.tm_mday = 1;

    //// Date/Time 
    //std::mktime(&tm);
    //std::time_t t = std::mktime(&tm);

    //std::cout << "UTC:   " << std::put_time(std::gmtime(&t), "%c %Z") << '\n';
    //std::cout << "local: " << std::put_time(std::localtime(&t), "%c %Z") << '\n';
    // VC version
    tm aTm{};
    aTm.tm_year = 2022 - 1900;
    aTm.tm_mday = 1;
    time_t t = mktime(&aTm);
    char timebuf[26] = {};
    gmtime_s(&aTm, &t);
    asctime_s(timebuf, 26, &aTm);
    std::cout << "UTC:   " << timebuf << '\n';
    localtime_s(&aTm, &t);
    asctime_s(timebuf, 26, &aTm);
    std::cout << "local: " << timebuf << '\n';


    // Capture now
    std::time_t start = std::time(nullptr);
    // do some waiting
    volatile double d = 1.0;

    // some time-consuming operation
    for (int p = 0; p < 10000; ++p)
        for (int q = 0; q < 100000; ++q)
            d = d + p * d * q + d;

    // Difference in time
    std::cout << "Wall time passed: " << std::difftime(std::time(nullptr), start) << " s.\n";

}

int main() {
    // Chrono usage example -- capture execution time [start part]
    const auto start{ std::chrono::steady_clock::now() };
    handleDateTime();

    Example_enum b;
    b = Example_enum::VALUE_ONE;
    switch (b) {
    case Example_enum::VALUE_ONE:
        std::cout << "enum with value of one" << std::endl;
        break;
    default:
        std::cout << "enum with NOT a value of one" << std::endl;
    }

    Example_struct example;

    std::cout << "Structure value number : " << example.number << std::endl;
    std::cout << "Structure value name : " << example.name << std::endl;
    example.number = 1;
    example.name = "Hello";
    std::cout << "Structure value number : " << example.number << std::endl;
    std::cout << "Structure value name : " << example.name << std::endl;

    Example_union exampleUnion;
    exampleUnion.numberI = 12;
    std::cout << "Union as Integer : " << exampleUnion.numberI << std::endl;
    std::cout << "Union as Float : " << exampleUnion.numberD << std::endl;
    exampleUnion.numberD = 98.5;
    std::cout << "Union as Integer : " << exampleUnion.numberI << std::endl;
    std::cout << "Union as Float : " << exampleUnion.numberD << std::endl;

    SimpleCPP simpleClass;
    std::cout << "Class number : " << simpleClass.getNumber() << std::endl;
    std::cout << "Class name : " << simpleClass.getName() << std::endl;

    simpleClass.setName("Bob");
    simpleClass.setNumber(5673);

    std::cout << "Class number : " << simpleClass.getNumber() << std::endl;
    std::cout << "Class name : " << simpleClass.getName() << std::endl;

    SimpleCPP initClass(18, "Rob");
    std::cout << "Class number : " << initClass.getNumber() << std::endl;
    std::cout << "Class name : " << initClass.getName() << std::endl;

    // Chrono usage example -- capture execution time [end part]
    const auto end{ std::chrono::steady_clock::now() };
    const std::chrono::duration<double> elapsed_seconds{ end - start };
    // requires c++ 20 for this line
    std::cout << "Program run for " << elapsed_seconds << '\n';
    return 0;
}