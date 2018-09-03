package com.learning.springboot.controller;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.learning.springboot.model.Country;
import com.learning.springboot.model.Language;
import com.learning.springboot.service.ICountryRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CountryRestController.class)
public class CountryRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ICountryRepository iCountryRepository;

	@Test
	public void retrieveCountryById() throws Exception {
		Country mockCountry = new Country();
		mockCountry.setId(155L);
		mockCountry.setName("Brazil");
		mockCountry.setCode("BRA");

		Language brazilian = new Language();
		brazilian.setId(156L);
		brazilian.setName("Brazilian");
		mockCountry.setLanguages(Arrays.asList(brazilian));
		
		Optional<Country> countryOptional = Optional.of(mockCountry);
		
		Mockito.when(iCountryRepository.findById(Mockito.anyLong())).thenReturn(countryOptional);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/countries/country/155").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains("Brazil"));
	}
}
