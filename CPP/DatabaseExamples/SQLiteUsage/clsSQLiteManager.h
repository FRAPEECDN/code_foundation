#pragma once
#include "sqlite3.h"
#include <string>
#include <iostream>
#include <map>
#include <vector>

// using for alias to make type usage easier
using Row = std::map<std::string, std::string>; // Represent single row of a return mapping column name to result
using ResultSet = std::vector<Row>; // represent a vector as return results, not adjusted for millions of records

/*
* Class for excapsulating SQLite (which is C code under C++ code, not it will use
* STL to enhance the code
* Also note the constructor and destructor need to clean up database pointer
* as well as any error message usage received from SQLite 
*/
class clsSQLiteManager
{
public:
	clsSQLiteManager();
	~clsSQLiteManager();
	bool openFile(std::string filename);
	bool closeFile();
	bool prepareAndExecuteSQL(std::string sqlStatement);
	bool prepareAndExecuteSELECT(std::string selectStatement, ResultSet& results);
	std::string getLastError();

protected:
	bool executeSQL(std::string sqlStatement);
	bool executeSQLWithCallBack(std::string sqlStatement, ResultSet& results);

private:
	sqlite3* db;	// Pointer for SQLite database
	std::string lastError; // keep track of last error
	
	// Specific callback function for SQLite (is used in executeSQLWithCallBack)
	static int callBackSQLResults(void* data, int argc, char** argv, char** azColName);
};

