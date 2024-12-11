#include <iostream>
#include <pqxx/pqxx> // Include the libpqxx header

void displayResults(pqxx::connection& conn) {
    try {
        pqxx::work txn(conn);
        pqxx::result result = txn.exec("SELECT * FROM People;");
        if (result.empty()) {
            std::cout << "No records found" << std::endl;
            return;
        }
        std::cout << "Records found : " << result.size() << std::endl;
        for (const auto& row : result) {
            std::cout << "| ";
            std::cout << "ID: " << row["id"].as<int>()
                << "| Firstname: " << row["FirstName"].as<std::string>()
                << "| Surname: " << row["LastName"].as<std::string>()
                << "| Age: " << row["age"].as<int>()
                << std::endl;
        }
    }
    catch (const std::exception& e) {
        std::cerr << "Error reading data: " << e.what() << std::endl;
    }
}

void createTable(pqxx::connection& conn) {
    try {
        pqxx::work txn(conn);
        txn.exec(
            "CREATE TABLE IF NOT EXISTS people ("
            "id SERIAL PRIMARY KEY, "
            "FirstName VARCHAR(100), "
            "LastName VARCHAR(100), "
            "age INT "
            ");"
        );
        txn.commit();
        std::cout << "Table created successfully." << std::endl;
    }
    catch (const std::exception& e) {
        std::cerr << "Error creating table: " << e.what() << std::endl;
    }
}

void insertData(pqxx::connection& conn) {
    try {
        pqxx::work txn(conn);
        txn.exec("INSERT INTO people (FirstName, LastName, age) VALUES ('Bob', 'Somebody', 30);");
        txn.exec("INSERT INTO people (FirstName, LastName, age) VALUES ('Alice', 'Nobody', 28);");
        txn.exec("INSERT INTO people (FirstName, LastName, age) VALUES ('John', 'Doe', 25);");
        txn.commit();
        std::cout << "Data inserted successfully." << std::endl;
    }
    catch (const std::exception& e) {
        std::cerr << "Error inserting data: " << e.what() << std::endl;
    }
}

void updateData(pqxx::connection& conn) {
    try {
        pqxx::work txn(conn);
        txn.exec("UPDATE people SET age = 35 WHERE FirstName = 'Alice' AND LastName = 'Nobody';");
        txn.commit();
        std::cout << "Data updated successfully." << std::endl;
    }
    catch (const std::exception& e) {
        std::cerr << "Error updating data: " << e.what() << std::endl;
    }
}

void deleteData(pqxx::connection& conn) {
    try {
        pqxx::work txn(conn);
        txn.exec("DELETE FROM people WHERE FirstName = 'John' AND LastName = 'Doe';");
        txn.commit();
        std::cout << "Data deleted successfully." << std::endl;
    }
    catch (const std::exception& e) {
        std::cerr << "Error deleting data: " << e.what() << std::endl;
    }
}


int main()
{
	const std::string connInfo = "dbname=test_one user=postgres password=G1j1m@ host=localhost port=5432";

    try {
        pqxx::connection conn(connInfo);
        if (conn.is_open()) {
            createTable(conn);
            displayResults(conn);

            insertData(conn);
            displayResults(conn);

            updateData(conn);
            displayResults(conn);

            deleteData(conn);
            displayResults(conn);
        }
    } catch (const std::exception& e) {
        std::cerr << "Error: " << e.what() << std::endl;
    }

    return 0;
}

