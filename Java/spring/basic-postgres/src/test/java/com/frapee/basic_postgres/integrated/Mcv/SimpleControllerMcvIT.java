package com.frapee.basic_postgres.integrated.Mcv;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.frapee.basic_postgres.dto.SimpleDto;
import com.frapee.basic_postgres.exceptions.EntityNotFoundException;
import com.frapee.basic_postgres.repository.SimpleRepository;
import com.frapee.basic_postgres.service.SimpleService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("it")
/**
 * Integrated Testing for the Controller
 * The service and other components is the actual components
 * Mcv provides the client mocking for calling the controller
 */
public class SimpleControllerMcvIT {

    private static final String PATH = "/api/simple";
    private static final String HEADER_KEY = "Content-Type";
    
    @Autowired
    private SimpleService service;

    @Autowired 
    SimpleRepository repository;

     @Autowired
    private JdbcTemplate jdbcTemplate;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:17-alpine"
    );      

    @Autowired
    private MockMvc mvc;

    private Gson gson = new Gson();

    @BeforeEach
    private void setupBaseData() {
        final List<SimpleDto> setupData = List.of(
            new SimpleDto(0, "apple"),
            new SimpleDto(0, "banana"),
            new SimpleDto(0, "grape"),
            new SimpleDto(0, "mango"),
            new SimpleDto(0, "nectarine"),
            new SimpleDto(0, "orange"),
            new SimpleDto(0, "pear"),
            new SimpleDto(0, "peach"),
            new SimpleDto(0, "pineapple"),
            new SimpleDto(0, "plum")
        );
        
        for (SimpleDto setup : setupData) {
            String insertSql = "INSERT INTO simple (name) VALUES (?)";
            jdbcTemplate.update(insertSql, setup.name());
        }
    }

    @AfterEach
    private void cleanupData() {
        String deleteSql = "DELETE FROM simple";
        jdbcTemplate.execute(deleteSql);
    }    

    @Test
    @DirtiesContext
    public void testGetAll() throws Exception {
        String expected = gson.toJson(service.getAll());
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(header().exists(HEADER_KEY))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                    .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(expected));
    }

    @Test
    @DirtiesContext
    public void testGetOne() throws Exception {
        final SimpleDto setupData = service.getOne(1);
        final String expected = gson.toJson(setupData);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", "1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(expected));
    }

    @Test
    @DirtiesContext
    public void testGetOneFail() throws Exception {
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", "-1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof EntityNotFoundException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
    }


    @Test
    @DirtiesContext
    public void testSearch() throws Exception {    
        String expected = gson.toJson(service.getAll());
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH + "/search")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        String actual = extractJson(result.getResponse().getContentAsString());
        assertThat(actual, equalTo(expected));
    }

    @Test
    @DirtiesContext
    public void testSearchWithPage() throws Exception {
        String expected = gson.toJson(service.getAll().subList(0, 5));
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH + "/search")
            .param("page", "0")
            .param("size", "5")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        String actual = extractJson(result.getResponse().getContentAsString());
        assertThat(actual, equalTo(expected));        
    }

    @Test
    @DirtiesContext
    public void testCreate() throws Exception {
        final SimpleDto setupData = new SimpleDto(10, "coconut");
        final String contentData = gson.toJson(setupData);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(PATH)
            .content(contentData)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(String.valueOf(contentData)));
    }

    @Test
    @DirtiesContext
    public void testUpdate() throws Exception {
        SimpleDto setupData = new SimpleDto(10, "coconut");
        String contentData = gson.toJson(setupData);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.put(PATH + "/{id}", "1")
            .content(contentData)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(contentData));
    }

    @Test
    @DirtiesContext
    public void testUpdateFailNoFound() throws Exception {
        SimpleDto setupData = new SimpleDto(10, "coconut");
        String contentData = gson.toJson(setupData);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.put(PATH + "/{id}", "-1")
            .content(contentData)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof EntityNotFoundException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @DirtiesContext
    public void testPatch() throws Exception {
        SimpleDto setupData = new SimpleDto(10, "coconut");
        String contentData = gson.toJson(setupData);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.patch(PATH + "/{id}", "1")
            .content(contentData)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(contentData));
    }

    @Test
    @DirtiesContext
    public void testPatchFailNoFound() throws Exception {
        SimpleDto setupData = new SimpleDto(10, "coconut");
        String contentData = gson.toJson(setupData);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.patch(PATH + "/{id}", "-1")
            .content(contentData)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof EntityNotFoundException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    @DirtiesContext
    public void testDelete() throws Exception {
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(PATH + "/{id}", "1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
            .andReturn();

        assertThat(result.getResponse().getContentType(), nullValue());
        assertThat(result.getResponse().getContentAsByteArray().length, equalTo(0));
    }    

    @Test
    @DirtiesContext
    public void testDeleteFail() throws Exception {
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(PATH + "/{id}", "-1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof EntityNotFoundException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
    }

    /**
     * Extract the JSON from result which will be contain content, pagable etc.
     * @param inputContent String representing actual mocked reponse content
     * @return extracted content JSON (which would be the list for testing)
     */
    private String extractJson(String inputContent) {
        try {
            JsonElement content = JsonParser.parseString(inputContent);
            JsonObject jObject = content.getAsJsonObject();
            JsonElement output = jObject.get("content");
            return gson.toJson(output);
        } catch (JsonParseException ex) {
            fail("Not Json");
        }
        return "";
    }
}
