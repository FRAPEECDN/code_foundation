package com.frapee.basic_postgres.integrated;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@ActiveProfiles("it")
class BasicPostgresApplicationTests {

	@Test
	void contextLoads() {
	}

}
