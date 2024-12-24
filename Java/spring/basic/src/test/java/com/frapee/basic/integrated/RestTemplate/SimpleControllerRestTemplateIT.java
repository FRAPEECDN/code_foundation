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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.frapee.basic.dto.SimpleDto;
import com.frapee.basic.service.SimpleService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleControllerRestTemplateIT {

    private static final String PATH = "/simple";

    @Autowired
    private SimpleService service;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    int randomServerPort;    
 
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
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<List<SimpleDto>> response = restTemplate.exchange(
            generatePath(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<SimpleDto>>(){}
        );

        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(service.getAll()));
    }

    @Test
    @DirtiesContext
    public void testGetOne() throws Exception {
        SimpleDto expected = service.getOne(1);
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<SimpleDto> response = restTemplate.exchange(
            generatePath() + "/1",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<SimpleDto>(){}
        );

        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(expected));
    }

    @Test
    @DirtiesContext
    public void testGetOneFail() throws Exception {
        final RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.exchange(
            generatePath() + "/10",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<SimpleDto>(){}
        );
        } catch (HttpClientErrorException exc) {
           assertThat(exc.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    @DirtiesContext
    @SuppressWarnings("null")
    public void testSearch() throws Exception {
        final List<SimpleDto> expected = service.getAll();
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<PagedModel<SimpleDto>> response = restTemplate.exchange(
            generatePath() + "/search",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<PagedModel<SimpleDto>>(){}
        );
        
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        final PagedModel<SimpleDto> pageBody = response.getBody();
        assertThat(pageBody, notNullValue());
        assertThat(pageBody.getContent(), notNullValue());
        final List<SimpleDto> actual = pageBody.getContent().stream().toList();
        assertThat(actual, equalTo(expected));
    }

    @Test
    @DirtiesContext
    @SuppressWarnings("null")
    public void testSearchWithPage() throws Exception {
        final List<SimpleDto> expected = service.getAll().subList(0, 5);
        final RestTemplate restTemplate = new RestTemplate();
        final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(generatePath() + "/search")
            .queryParam("page", "0")
            .queryParam("size", "5");

        final ResponseEntity<PagedModel<SimpleDto>> response = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<PagedModel<SimpleDto>>(){}
        );
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        final PagedModel<SimpleDto> pageBody = response.getBody();
        assertThat(pageBody, notNullValue());
        assertThat(pageBody.getContent(), notNullValue());
        final List<SimpleDto> actual = pageBody.getContent().stream().toList();
        assertThat(actual, equalTo(expected));
    }

    @Test
    @DirtiesContext
    public void testCreate() throws Exception {
        SimpleDto contentData = new SimpleDto(11, "coconut");
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<SimpleDto> httpEntity = new HttpEntity<>(contentData, httpHeaders);        
        final ResponseEntity<SimpleDto> response = restTemplate.exchange(
            generatePath(),
            HttpMethod.POST,
            httpEntity,
            new ParameterizedTypeReference<SimpleDto>(){}
        );
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat(response.getBody(), equalTo(contentData));
    }

    @Test
    @DirtiesContext
    public void testUpdate() throws Exception {
        SimpleDto contentData = new SimpleDto(1, "coconut");
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<SimpleDto> httpEntity = new HttpEntity<>(contentData, httpHeaders);        
        final ResponseEntity<SimpleDto> response = restTemplate.exchange(
            generatePath() + "/1",
            HttpMethod.PUT,
            httpEntity,
            new ParameterizedTypeReference<SimpleDto>(){}
        );
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(contentData));        
    }

    @Test
    @DirtiesContext
    public void testUpdateFailNoFound() throws Exception {
        SimpleDto contentData = new SimpleDto(10, "coconut");
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<SimpleDto> httpEntity = new HttpEntity<>(contentData, httpHeaders);        
        try {
            restTemplate.exchange(
            generatePath() + "/10",
            HttpMethod.PUT,
            httpEntity,
            new ParameterizedTypeReference<SimpleDto>(){}
        );
        } catch (HttpClientErrorException exc) {
           assertThat(exc.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        }        
    }

    @Test
    @DirtiesContext
    public void testPatch() throws Exception {
        SimpleDto contentData = new SimpleDto(1, "coconut");
        final RestTemplate restTemplate = new RestTemplate(new JdkClientHttpRequestFactory());
        final HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<SimpleDto> httpEntity = new HttpEntity<>(contentData, httpHeaders);        
        final ResponseEntity<SimpleDto> response = restTemplate.exchange(
            generatePath() + "/1",
            HttpMethod.PATCH,
            httpEntity,
            new ParameterizedTypeReference<SimpleDto>(){}
        );
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(contentData)); 
    }

    @Test
    @DirtiesContext
    public void testPatchFailNoFound() throws Exception {
        SimpleDto contentData = new SimpleDto(10, "coconut");
        final RestTemplate restTemplate = new RestTemplate(new JdkClientHttpRequestFactory());
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<SimpleDto> httpEntity = new HttpEntity<>(contentData, httpHeaders);        
        try {
            restTemplate.exchange(
            generatePath() + "/10",
            HttpMethod.PATCH,
            httpEntity,
            new ParameterizedTypeReference<SimpleDto>(){}
        );
        } catch (HttpClientErrorException exc) {
           assertThat(exc.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        }      
    }

    @Test
    @DirtiesContext
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
    @DirtiesContext
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
