package com.frapee.basic.controller;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.frapee.basic.dto.SimpleDto;
import com.frapee.basic.exceptions.EntityNotFoundException;
import com.frapee.basic.exceptions.GeneralServiceException;
import com.frapee.basic.service.SimpleService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

@WebMvcTest(SimpleController.class)
/**
 * Unit testing the controller part.
 * The service is being mocked (as created Bean) so that the controller can be tested independently
 * Mcv provides the client mocking for calling the controller
 */
public class TestSimpleController {

    private static final String PATH = "/simple";
    private static final String HEADER_KEY = "Content-Type";
    
    @MockBean
    private SimpleService service;

    @Autowired
    private MockMvc mvc;

    private Gson gson = new Gson();

    @Test
    public void testGetAll() throws Exception {
        final List<SimpleDto> setupData = List.of(
                new SimpleDto(1, "apple"),
                new SimpleDto(2,"grape"),
                new SimpleDto(3,"orange"),
                new SimpleDto(4,"pear"),
                new SimpleDto(5,"peach"),
                new SimpleDto(6,"plum")
        );
        String expected = gson.toJson(setupData);
        when(service.getAll()).thenReturn(setupData);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(header().exists(HEADER_KEY))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                    .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(expected));
        Mockito.verify(this.service, Mockito.times(1)).getAll();
    }

    @Test
    public void testGetOne() throws Exception {
        final SimpleDto setupData = new SimpleDto(1, "apple");
        final String expected = gson.toJson(setupData);
        when(service.getOne(isA(Integer.class))).thenReturn(setupData);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", "1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(expected));
        Mockito.verify(this.service, Mockito.times(1)).getOne(isA(Integer.class));
    }

    @Test
    public void testGetOneFail() throws Exception {
        when(service.getOne(isA(Integer.class))).thenThrow(EntityNotFoundException.class);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", "1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof EntityNotFoundException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
        Mockito.verify(this.service, Mockito.times(1)).getOne(isA(Integer.class));
    }


    @Test
    public void testSearch() throws Exception {
        final List<SimpleDto> setupData = List.of(
                new SimpleDto(1, "apple"),
                new SimpleDto(2,"banana"),
                new SimpleDto(3,"grape"),
                new SimpleDto(4,"mango"),
                new SimpleDto(5,"nectarine"),
                new SimpleDto(5,"orange"),
                new SimpleDto(5,"pear"),
                new SimpleDto(5,"peach"),
                new SimpleDto(5,"pineapple"),
                new SimpleDto(6,"plum")
        );        
        String expected = gson.toJson(setupData);
        when(service.getAll()).thenReturn(setupData);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH + "/search")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andReturn();

        String actual = extractJson(result.getResponse().getContentAsString());
        assertThat(actual, equalTo(expected));
        Mockito.verify(this.service, Mockito.times(1)).getAll();
    }

    @Test
    public void testSearchWithPage() throws Exception {
        final List<SimpleDto> setupData = List.of(
                new SimpleDto(1, "apple"),
                new SimpleDto(2,"banana"),
                new SimpleDto(3,"grape"),
                new SimpleDto(4,"mango"),
                new SimpleDto(5,"nectarine"),
                new SimpleDto(5,"orange"),
                new SimpleDto(5,"pear"),
                new SimpleDto(5,"peach"),
                new SimpleDto(5,"pineapple"),
                new SimpleDto(6,"plum")
        ); 
        String expected = gson.toJson(setupData.subList(0, 5));
        when(service.getAll()).thenReturn(setupData);
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
        Mockito.verify(this.service, Mockito.times(1)).getAll();
    }

    @Test
    public void testCreate() throws Exception {
        final SimpleDto setupData = new SimpleDto(10, "coconut");
        final String contentData = gson.toJson(setupData);
        when(service.createOne(isA(SimpleDto.class))).thenReturn(setupData);
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
        Mockito.verify(this.service, Mockito.times(1)).createOne(isA(SimpleDto.class));
    }

    @Test
    public void testCreateFail() throws Exception {
        SimpleDto setupData = new SimpleDto(10, "coconut");
        String contentData = gson.toJson(setupData);
        when(service.createOne(isA(SimpleDto.class))).thenThrow(GeneralServiceException.class);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(PATH)
            .content(contentData)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof GeneralServiceException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
        Mockito.verify(this.service, Mockito.times(1)).createOne(isA(SimpleDto.class));
    }

    @Test
    public void testUpdate() throws Exception {
        SimpleDto setupData = new SimpleDto(10, "coconut");
        String contentData = gson.toJson(setupData);
        when(service.updateOne(isA(Integer.class), isA(SimpleDto.class))).thenReturn(setupData);
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
        Mockito.verify(this.service, Mockito.times(1)).updateOne(isA(Integer.class), isA(SimpleDto.class));
    }

    @Test
    public void testUpdateFailNoFound() throws Exception {
        SimpleDto setupData = new SimpleDto(10, "coconut");
        String contentData = gson.toJson(setupData);
        when(service.updateOne(isA(Integer.class), isA(SimpleDto.class))).thenThrow(EntityNotFoundException.class);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.put(PATH + "/{id}", "1")
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
        Mockito.verify(this.service, Mockito.times(1)).updateOne(isA(Integer.class), isA(SimpleDto.class));
    }

    @Test
    public void testUpdateFailInvalidData() throws Exception {
        SimpleDto setupData = new SimpleDto(10, "coconut");
        String contentData = gson.toJson(setupData);
        when(service.updateOne(isA(Integer.class), isA(SimpleDto.class))).thenThrow(GeneralServiceException.class);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.put(PATH + "/{id}", "1")
            .content(contentData)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof GeneralServiceException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
        Mockito.verify(this.service, Mockito.times(1)).updateOne(isA(Integer.class), isA(SimpleDto.class));
    }

    @Test
    public void testPatch() throws Exception {
        SimpleDto setupData = new SimpleDto(10, "coconut");
        String contentData = gson.toJson(setupData);
        when(service.updateOne(isA(Integer.class), isA(SimpleDto.class))).thenReturn(setupData);
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
        Mockito.verify(this.service, Mockito.times(1)).updateOne(isA(Integer.class), isA(SimpleDto.class));
    }

    @Test
    public void testPatchFailNoFound() throws Exception {
        SimpleDto setupData = new SimpleDto(10, "coconut");
        String contentData = gson.toJson(setupData);
        when(service.updateOne(isA(Integer.class), isA(SimpleDto.class))).thenThrow(EntityNotFoundException.class);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.patch(PATH + "/{id}", "1")
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
        Mockito.verify(this.service, Mockito.times(1)).updateOne(isA(Integer.class), isA(SimpleDto.class));
    }

    @Test
    public void testPatchFailInvalidData() throws Exception {
        SimpleDto setupData = new SimpleDto(10, "coconut");
        String contentData = gson.toJson(setupData);
        when(service.updateOne(isA(Integer.class), isA(SimpleDto.class))).thenThrow(GeneralServiceException.class);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.patch(PATH + "/{id}", "1")
            .content(contentData)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof GeneralServiceException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
        Mockito.verify(this.service, Mockito.times(1)).updateOne(isA(Integer.class), isA(SimpleDto.class));
    }

    @Test
    public void testDelete() throws Exception {
        Mockito.doNothing().when(service).deleteOne(isA(Integer.class));
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(PATH + "/{id}", "1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
            .andReturn();

        assertThat(result.getResponse().getContentType(), nullValue());
        assertThat(result.getResponse().getContentAsByteArray().length, equalTo(0));
        Mockito.verify(this.service, Mockito.times(1)).deleteOne(isA(Integer.class));
    }    

    @Test
    public void testDeleteFail() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(service).deleteOne(isA(Integer.class));
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(PATH + "/{id}", "1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(header().exists(HEADER_KEY))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(expected -> assertTrue(expected.getResolvedException() instanceof EntityNotFoundException))
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), notNullValue());
        Mockito.verify(this.service, Mockito.times(1)).deleteOne(isA(Integer.class));
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
