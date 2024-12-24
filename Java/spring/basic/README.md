# Spring 6 Boot 3 Basic demo
The application is the basic demo for Spring Boot implementation using Java 21 with Spring 6 and Boot 3.
It uses spring-boot-starter-parent to configure the version being used
Additionaly it uses hamcrest for testing to make sure values match

- Testxxx indicates a unit test
- xxIT indicates integrated test
- Refer to file HELP.md [a relative link](HELP.md) to documentation access links

## Database
- The database is HyperSQL running an actual server/file for the main application.
- The database is configured for integrated test to run in memory mode
- The database for runtime mode can be started using instructions in [a relative link](DB_START.md)

## Application.properties
- First Basic demo uses no profiles
- All application properties are configured in default application.properties file.
- Both main and test will each have the specific file

## Running server
- Running the server creates basic api location with not content path
- Use http://localhost:8080/v3/api-docs to see OpenAPI generated
- Use http://localhost:8080/swagger-ui/index.html to access OpenAPI published