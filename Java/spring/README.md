# Spring 6 Boot 3
The foundation is for implementing Spring 6 Boot 3 for starters
Java 21 is used for Java version

- Testxxx indicates a unit test
- xxIT indicates integrated test
- Refer to file HELP.md [a relative link](HELP.md) to documentation access links

## Database
- The database is HyperSQL and PostgresSQL for JPA Hibernate
- It include using in memory for unit testing
- The database can run either as container or in-memory for Integrated Tests

## Security
- The JPA demos (basic and basic-postgres), will be running with no security
- securityDemo will use Basic Authorization
- JwtsecurityDemo will use JWT Token security

## Application Cnfigurations
- The JPA demos (basic and basic-postgres) and security demos will just use application.properties
- BasicWithSecurity will also yml file for settings
- basic-prostgres and BasicWithSecurity will use Spring Profiles

## Running server
OpenAPI 3.0 with UI should be active for all foundational examples
- Use http://localhost:8080/v3/api-docs to see OpenAPI generated
- Use http://localhost:8080/swagger-ui/index.html to access OpenAPI published