package com.frapee.basic_postgres.integrated;

import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;

import com.frapee.basic_postgres.BasicPostgresApplication;

@ActiveProfiles("it")
public class TestBasicPostgresApplication {

	public static void main(String[] args) {
		SpringApplication.from(BasicPostgresApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
