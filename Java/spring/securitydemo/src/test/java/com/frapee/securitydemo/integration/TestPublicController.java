package com.frapee.securitydemo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.frapee.securitydemo.controller.PublicController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestPublicController {

    private static final String PATH = "/public";
    private static final String HEADER_KEY = "Content-Type";

    @Autowired
    private MockMvc mvc;
    
    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testGetAsAdmin() throws Exception {
        String expected = PublicController.MESSAGE;
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(header().exists(HEADER_KEY))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn();

        assertEquals(expected, result.getResponse().getContentAsString());
    }
    
    @Test
    @WithMockUser(roles={"USER"})
    public void testGetAsUser() throws Exception {
        String expected = PublicController.MESSAGE;
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(header().exists(HEADER_KEY))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn();

        assertEquals(expected, result.getResponse().getContentAsString());
    } 
  
    @Test
    @WithMockUser(roles={})
    public void testGetAsNoRole() throws Exception {
        String expected = PublicController.MESSAGE;
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(header().exists(HEADER_KEY))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn();

        assertEquals(expected, result.getResponse().getContentAsString());
    } 

    @Test
    @WithAnonymousUser
    public void testGetAnyUser() throws Exception {
        String expected = PublicController.MESSAGE;
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(PATH)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(header().exists(HEADER_KEY))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn();

        assertEquals(expected, result.getResponse().getContentAsString());
    }
    
}
