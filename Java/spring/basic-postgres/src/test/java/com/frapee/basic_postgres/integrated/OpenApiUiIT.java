package com.frapee.basic_postgres.integrated;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class OpenApiUiIT {

    @Autowired
    private MockMvc mvc;

	@Test
	public void shouldDisplaySwaggerUiPage() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
            .get("/swagger-ui/index.html"))
            .andExpect(status().isOk())
            .andReturn();

		String contentAsString = mvcResult.getResponse().getContentAsString();
		assertTrue(contentAsString.contains("Swagger UI"));
	}
	
}
