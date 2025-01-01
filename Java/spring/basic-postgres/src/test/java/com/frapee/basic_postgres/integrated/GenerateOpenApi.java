package com.frapee.basic_postgres.integrated;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class GenerateOpenApi {

    @Autowired
    private MockMvc mvc;

    	@Test
	public void generateOpenApiJson() throws Exception {
        mvc.perform(MockMvcRequestBuilders
            .get("/v3/api-docs")
            .accept(MediaType.APPLICATION_JSON))
            .andDo((result) -> {
        	    FileUtils.writeStringToFile(new File("src/test/openapi/openapi3.json"), 
                    result.getResponse().getContentAsString(), Charset.defaultCharset(), false);
            });		
	}
	
}
