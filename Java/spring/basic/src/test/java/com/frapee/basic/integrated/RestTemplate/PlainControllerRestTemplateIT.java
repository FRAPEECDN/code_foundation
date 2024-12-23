package com.frapee.basic.integrated.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.frapee.basic.service.StringService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlainControllerRestTemplateIT {

    private static final String PATH = "/plain";

    @Autowired
    private StringService service;

    @LocalServerPort
    int randomServerPort;    
 
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
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<List<String>> response = restTemplate.exchange(
            generatePath(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<String>>(){}
        );

        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(service.getAll()));
    }

    @Test
    public void testGetOne() throws Exception {
        String expected = service.getOne(1);
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<String> response = restTemplate.exchange(
            generatePath() + "/1",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<String>(){}
        );

        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(expected));
    }

    @Test
    public void testGetOneFail() throws Exception {
        final RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.exchange(
            generatePath() + "/10",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<String>(){}
        );
        } catch (HttpClientErrorException exc) {
           assertThat(exc.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    @SuppressWarnings("null")
    public void testSearch() throws Exception {
        final List<String> expected = service.getAll();
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<PagedModel<String>> response = restTemplate.exchange(
            generatePath() + "/search",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<PagedModel<String>>(){}
        );
        
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        final PagedModel<String> pageBody = response.getBody();
        assertThat(pageBody, notNullValue());
        assertThat(pageBody.getContent(), notNullValue());
        final List<String> actual = pageBody.getContent().stream().toList();
        assertThat(actual, equalTo(expected));
    }

    @Test
    @SuppressWarnings("null")
    public void testSearchWithPage() throws Exception {
        final List<String> expected = service.getAll().subList(0, 5);
        final RestTemplate restTemplate = new RestTemplate();
        final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(generatePath() + "/search")
            .queryParam("page", "0")
            .queryParam("size", "5");

        final ResponseEntity<PagedModel<String>> response = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<PagedModel<String>>(){}
        );
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        final PagedModel<String> pageBody = response.getBody();
        assertThat(pageBody, notNullValue());
        assertThat(pageBody.getContent(), notNullValue());
        final List<String> actual = pageBody.getContent().stream().toList();
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testCreate() throws Exception {
        String contentData = "coconut";
        Integer expected = 10;
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<String> httpEntity = new HttpEntity<>(contentData, httpHeaders);        
        final ResponseEntity<Integer> response = restTemplate.exchange(
            generatePath(),
            HttpMethod.POST,
            httpEntity,
            new ParameterizedTypeReference<Integer>(){}
        );
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat(response.getBody(), equalTo(expected));
    }

    @Test
    public void testCreateFail() throws Exception {
        String contentData = "apple";
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<String> httpEntity = new HttpEntity<>(contentData, httpHeaders);           
        try {
            restTemplate.exchange(
            generatePath(),
            HttpMethod.POST,
            httpEntity,
            new ParameterizedTypeReference<String>(){}
        );
        } catch (HttpServerErrorException exc) {
           assertThat(exc.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @Test
    public void testUpdate() throws Exception {
        String contentData = "coconut";
        String expected = contentData;
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<String> httpEntity = new HttpEntity<>(contentData, httpHeaders);        
        final ResponseEntity<String> response = restTemplate.exchange(
            generatePath() + "/1",
            HttpMethod.PUT,
            httpEntity,
            new ParameterizedTypeReference<String>(){}
        );
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(expected));        
    }

    @Test
    public void testUpdateFailNoFound() throws Exception {
        String contentData = "coconut";
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<String> httpEntity = new HttpEntity<>(contentData, httpHeaders);        
        try {
            restTemplate.exchange(
            generatePath() + "/10",
            HttpMethod.PUT,
            httpEntity,
            new ParameterizedTypeReference<String>(){}
        );
        } catch (HttpClientErrorException exc) {
           assertThat(exc.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        }        
    }

    @Test
    public void testPatch() throws Exception {
        String contentData = "coconut";
        String expected = contentData;
        final RestTemplate restTemplate = new RestTemplate(new JdkClientHttpRequestFactory());
        final HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(contentData, httpHeaders);        
        final ResponseEntity<String> response = restTemplate.exchange(
            generatePath() + "/1",
            HttpMethod.PATCH,
            httpEntity,
            new ParameterizedTypeReference<String>(){}
        );
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(expected)); 
    }

    @Test
    public void testPatchFailNoFound() throws Exception {
        String contentData = "coconut";
        final RestTemplate restTemplate = new RestTemplate(new JdkClientHttpRequestFactory());
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<String> httpEntity = new HttpEntity<>(contentData, httpHeaders);        
        try {
            restTemplate.exchange(
            generatePath() + "/10",
            HttpMethod.PATCH,
            httpEntity,
            new ParameterizedTypeReference<String>(){}
        );
        } catch (HttpClientErrorException exc) {
           assertThat(exc.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        }      
    }

    @Test
    public void testDelete() throws Exception {
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<Object> response = restTemplate.exchange(
            generatePath() + "/1",
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<Object>(){}
        );
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
    }    

    @Test
    public void testDeleteFail() throws Exception {
        final RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.exchange(
                generatePath() + "/10",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Object>(){}
            );
        } catch (HttpClientErrorException exc) {
            assertThat(exc.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        }      
    }

    /**
     * Create the url to be used for testing by providing the correct port and path
     * @return full URL to be used in integrated tests
     */
    private String generatePath() {
        return "http://localhost:" + randomServerPort + PATH;
    }

}
