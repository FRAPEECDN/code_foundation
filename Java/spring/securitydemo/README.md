# Spring 6 Boot 3 Security demo
The application is the basic demo for Spring Boot implementation using Java 21 with Spring 6 and Boot 3.
It uses spring-boot-starter-parent to configure the version being used
It focus on security but very basic, aka login 

- Testxxx indicates a unit test
- xxIT indicates integrated test
- Refer to file HELP.md [a relative link](HELP.md) to documentation access links

## Running server
- When running a login will be required
- http://localhost:8080/logout - to log out
- http://localhost:8080/public - should always display (no security)
- http://localhost:8080/api - When having role user it should display
- http://localhost:8080/admin - WHen having role admin it should display
