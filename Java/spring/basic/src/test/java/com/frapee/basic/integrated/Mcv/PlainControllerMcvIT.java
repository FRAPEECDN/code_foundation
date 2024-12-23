package com.frapee.basic.integrated.Mcv;

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
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.frapee.basic.exceptions.GeneralServiceException;
import com.frapee.basic.service.StringService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
/**
 * Integrated Testing for the Controller
 * The service and other components is the actual components
 * Mcv provides the client mocking for calling the controller
 */
public class PlainControllerMcvIT {

    private static final String PATH = "/plain";
    private static final String HEADER_KEY = "Content-Type";
    
    @Autowired
    private StringService service;

    @Autowired
    private MockMvc mvc;

    private Gson gson = new Gson();

    @BeforeEach
    private void setupBaseData() {
        service.resetRepositoryList();
        final List<String> setupData = List.of(
                "apple",
                "banana",
                "grape",
                "mango",
                "nectarine",
                "orange",
                "pear",
                "peach",
                "pineapple",
                "plum"
                
        );
        service.setInternalStrings(setupData);
    }

    @AfterEach
    private void cleanupData() {
        service.resetRepositoryList();
    }

    @Test
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
    public void testGetOne() throws Exception {
        String setupData = service.getOne(1);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", "1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(setupData));
    }

    @Test
    public void testGetOneFail() throws Exception {
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", "-1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof ResourceNotFoundException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
    }


    @Test
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
    public void testCreate() throws Exception {
        String contentData = "coconut";
        Integer setupData = 10;
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(PATH)
            .content(contentData)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(String.valueOf(setupData)));
    }

    @Test
    public void testCreateFail() throws Exception {
        String contentData = "apple";
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(PATH)
            .content(contentData)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof GeneralServiceException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    public void testUpdate() throws Exception {

        String contentData = "coconut";
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.put(PATH + "/{id}", "1")
            .content(contentData)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(contentData));
    }

    @Test
    public void testUpdateFailNoFound() throws Exception {
        String contentData = "coconut";
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.put(PATH + "/{id}", "-1")
            .content(contentData)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof ResourceNotFoundException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
    }

    @Test
    public void testPatch() throws Exception {
        String contentData = "coconut";
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.patch(PATH + "/{id}", "1")
            .content(contentData)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(contentData));
    }

    @Test
    public void testPatchFailNoFound() throws Exception {
        String contentData = "coconut";
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.patch(PATH + "/{id}", "-1")
            .content(contentData)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof ResourceNotFoundException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
    }

    @Test
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
    public void testDeleteFail() throws Exception {
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(PATH + "/{id}", "-1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof ResourceNotFoundException))
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
