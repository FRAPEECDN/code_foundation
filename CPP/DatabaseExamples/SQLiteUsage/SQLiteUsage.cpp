#include <iostream>
#include <tuple>
#include "clsSQLiteManager.h"

static void displayResult(ResultSet results) {
    if (results.empty()) {
        std::cout << "Nothing found" << std::endl;
    }
    else {
        std::cout << "Found " << std::to_string(results.size()) << " records" << std::endl;
    }
    for (const auto& row : results) {
        std::cout << "| ";
        for (const auto& [colName, value] : row) {
            std::cout << colName << ": " << value << " | ";
        }
        std::cout << std::endl;
    }
}

static void checkQuery(clsSQLiteManager& db) {
    const std::string selectQuery = R"(SELECT * FROM People;)";

    ResultSet result;
    db.prepareAndExecuteSELECT(selectQuery, result);
    displayResult(result);
}


int main()
{
    clsSQLiteManager db;
    std::vector<std::tuple<std::string, std::string, int>> listPeopleGenerator = {
        { "Bob", "Somebody", 30 },
        { "Alice", "Nobody", 28 },
        { "John", "Doe", 25 }
    };

    if (!db.openFile("exampleDb")) {
        std::cout << "failed to open" << std::endl;
    }

    // Using Raw String literal R
    const std::string createQuery = R"(
        CREATE TABLE IF NOT EXISTS People (
            ID INTEGER PRIMARY KEY AUTOINCREMENT,
            FirstName TEXT NOT NULL,
            Surname TEXT NOT NULL,
            Age INTEGER NOT NULL
        );
    )";

    if (!db.prepareAndExecuteSQL(createQuery)) {
        std::cout << "failed to create table" << std::endl;
    }
    checkQuery(db);

    std::string insertQuery = "";
    for (auto person : listPeopleGenerator) {
        const auto [name, surname, age] = person;
        std::string personLine = "INSERT INTO People(FirstName, Surname, Age) VALUES (";
        personLine += "'";
        personLine += name;
        personLine += "' , ";
        personLine += "'";
        personLine += surname;
        personLine += "' , ";
        personLine += std::to_string(age);
        personLine += ");";
        insertQuery += personLine;
    }

    if (!db.prepareAndExecuteSQL(insertQuery)) {
        std::cout << "insertion failed" << std::endl;
    }
    checkQuery(db);

    std::string updateQuery = "UPDATE People set Age = 35 WHERE FirstName = 'Alice' AND Surname = 'Nobody';";
    if (!db.prepareAndExecuteSQL(updateQuery)) {
        std::cout << "update failed" << std::endl;
    }
    checkQuery(db);

    std::string deleteQuery = "DELETE FROM People WHERE FirstName = 'John' AND Surname = 'Doe';";
    if (!db.prepareAndExecuteSQL(deleteQuery)) {
        std::cout << "delete failed" << std::endl;
    }
    checkQuery(db);

    db.closeFile();
}

