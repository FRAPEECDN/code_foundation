#include "clsSQLiteManager.h"
#include <regex>

/*******************************************************************************************************************************
* Constructor and Destructor
********************************************************************************************************************************/

clsSQLiteManager::clsSQLiteManager()
{
	db = nullptr;
}

clsSQLiteManager::~clsSQLiteManager()
{
	closeFile();
}

/*******************************************************************************************************************************
* Public section
********************************************************************************************************************************/

/*
* Opens the database and file associated with the database
* Error happens if file is not found (path is incorrect)
*/
bool clsSQLiteManager::openFile(std::string filename)
{
	if (db != nullptr) {
		return false; // Have db already opened, so no error
	}
	int rc = sqlite3_open(filename.c_str(), &db);
	if (rc) {
		lastError.assign(sqlite3_errmsg(db));
		std::cerr << "Error opening database: " << lastError << std::endl;
		return false;
	}

	return true;
}

/*
* Close the database and file associated with the database
*/
bool clsSQLiteManager::closeFile()
{
	if (db != nullptr) {
		sqlite3_close(db);
		db = nullptr;
		return true;
	}
	else {
		return false;
	}
}

/*
* Prepares any sql statement without expecting a result set, common statements are Table commands, INSERT, UPDATE and DELETE
*/
bool clsSQLiteManager::prepareAndExecuteSQL(std::string sqlStatement)
{
	std::regex self_regex("SELECT", std::regex_constants::ECMAScript | std::regex_constants::icase);
	if (std::regex_search(sqlStatement, self_regex)) {
		lastError.assign("SQL contains invalid 'select' statement");
		return false;
	}

	return executeSQL(sqlStatement);
}

/*
* Pepares sql SELECT statement, not expecting a result set containing returned values
*/
bool clsSQLiteManager::prepareAndExecuteSELECT(std::string selectStatement, ResultSet& results)
{
	std::regex self_regex("SELECT", std::regex_constants::ECMAScript | std::regex_constants::icase);
	if (!std::regex_search(selectStatement, self_regex)) {
		lastError.assign("SQL is not a 'select' statement");
		return false;
	}

	return executeSQLWithCallBack(selectStatement, results);
}

/*
* Getter for last error
*/
std::string clsSQLiteManager::getLastError()
{
	return lastError;
}

/*******************************************************************************************************************************
* Protected Section
********************************************************************************************************************************/

/*
* Execution of sql statements, can be used for insert, update, delete
* Can be used for create and drop table commands as well
*/
bool clsSQLiteManager::executeSQL(std::string sqlStatement)
{
	char* errorMessage = nullptr;
	int rc = sqlite3_exec(db, sqlStatement.c_str(), nullptr, nullptr, &errorMessage);

	if (rc != SQLITE_OK) {
		lastError.assign(errorMessage);
		sqlite3_free(errorMessage);
		std::cerr << "SQL execution error: " << lastError << std::endl;
		return false;
	} 
	return true;
}

/*
* Execution of sql statement, used for select statements
* Call back function, still in standard c form since sqlite uses c form calling
*/
bool clsSQLiteManager::executeSQLWithCallBack(std::string sqlStatement, ResultSet& results)
{
	char* errorMessage = nullptr;
	int rc = sqlite3_exec(db, sqlStatement.c_str(), callBackSQLResults, &results, &errorMessage);

	if (rc != SQLITE_OK) {
		lastError.assign(errorMessage);
		sqlite3_free(errorMessage);
		std::cerr << "SQL execution error: " << lastError << std::endl;
		return false;
	}
	return true;
}

/*******************************************************************************************************************************
* Private Section
********************************************************************************************************************************/

/*
* Traditional C callback function, see parameter descriptions
* data -> return the processed data for the function
* argc -> count column returns
* argv -> column values
* azColName -> contains the name of the column
*/
int clsSQLiteManager::callBackSQLResults(void* data, int argc, char** argv, char** azColName)
{
	// Cast the void pointer to a ResultSet pointer for returning results as filled up
	auto* result = static_cast<ResultSet*>(data);

	Row row;
	for (int i = 0; i < argc; i++) {
		// Check for null values and assign them appropriately
		row[azColName[i]] = argv[i] ? argv[i] : "NULL";
	}

	// Add a new row to the result set
	result->emplace_back(row);
	return 0; // Success
}
