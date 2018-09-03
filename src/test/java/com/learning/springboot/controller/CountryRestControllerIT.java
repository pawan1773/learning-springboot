package com.learning.springboot.controller;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.learning.springboot.LearningSpringbootApplication;
import com.learning.springboot.model.Country;
import com.learning.springboot.model.Language;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearningSpringbootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryRestControllerIT {

	@LocalServerPort
	private int port;

	private TestRestTemplate template = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Before
	public void setupJSONAcceptType() {
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}

	@Test
	public void fetchCountryLanguage() throws Exception {

		String expected = "{\"id\":2,\"name\":\"Italy\",\"code\":\"IT \",\"languages\":[{\"id\":3,\"name\":\"Italian\"},{\"id\":4,\"name\":\"Fruili\"}]}";

		ResponseEntity<String> response = template.exchange(createUrl("/countries/country/2"), HttpMethod.GET,
				new HttpEntity<String>("DUMMY_DOESNT_MATTER", headers), String.class);

		JSONAssert.assertEquals(expected, response.getBody(), true);
	}

	@Test
	public void retrieveCountryLanguage() throws Exception {

		ResponseEntity<Country> response = template.exchange(createUrl("/countries/country/2"), HttpMethod.GET,
				new HttpEntity<String>("DUMMY_HEADER", headers), new ParameterizedTypeReference<Country>() {
				});

		assertTrue(((Country) response.getBody()).getName().equals("Italy"));
	}

	@Test
	public void createSurveyQuestion() throws Exception {
		Country country = new Country();
		country.setName("Brazil");
		country.setCode("BRA");

		Language brazilian = new Language();
		brazilian.setName("Brazilian");

		country.setLanguages(Arrays.asList(brazilian));
		
		HttpEntity<Country> entity = new HttpEntity<>(country, headers);

		ResponseEntity<String> response = template.exchange(createUrl("/countries/country"), HttpMethod.POST,
				entity, String.class);

		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
		assertTrue(actual.contains("/countries/country/"));
	}

	private String createUrl(String uri) {
		return "http://localhost:" + port + uri;
	}

}
