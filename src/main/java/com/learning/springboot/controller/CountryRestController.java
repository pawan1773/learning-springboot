package com.learning.springboot.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learning.springboot.model.Country;
import com.learning.springboot.model.Language;
import com.learning.springboot.service.ICountryRepository;

@RestController
@RequestMapping("/countries")
public class CountryRestController {

	private final ICountryRepository iCountryRepository;

	/**
	 * Injecting ICountryRepository dependency into CountryRestController.
	 * 
	 * @param iCountryRepository
	 */
	@Autowired
	public CountryRestController(ICountryRepository iCountryRepository) {
		this.iCountryRepository = iCountryRepository;
	}

	/**
	 * Method handling HTTP GET requests to fetch all the countries along with
	 * languages associated with them.
	 * 
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Country>> retrieveAllCountries() {
		List<Country> countries = this.iCountryRepository.findAll();
		if (null == countries) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(countries);
	}

	/**
	 * Method handling HTTP GET requests to fetch a country along with languages
	 * associated with them.
	 * 
	 * @return
	 */
	@GetMapping("/country/{id}")
	public ResponseEntity<Country> retrieveCountryById(@PathVariable Long id) {
		Optional<Country> country = this.iCountryRepository.findById(id);
		if (!country.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(country.get());
	}

	/**
	 * Method handling HTTP POST requests to save country details
	 * 
	 * @return
	 */
	@PostMapping("/country")
	public ResponseEntity<?> postCountryDetails(@RequestBody Country country) {
		Country newCountry = new Country();
		newCountry.setName(country.getName());
		newCountry.setCode(country.getCode());

		List<Language> languages = new ArrayList<>();
		List<Language> temp = country.getLanguages();
		if (null != temp && !temp.isEmpty()) {
			for (Language language : temp) {
				languages.add(new Language(language.getName(), newCountry));
			}
		}
		newCountry.setLanguages(languages);

		Country savedCountry = this.iCountryRepository.save(newCountry);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCountry.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * Method handling HTTP PUT requests to update country details.
	 * 
	 * @return
	 */
	@PutMapping("/country/{id}")
	public ResponseEntity<Country> updateCountryById(@PathVariable Long id, @RequestBody Country country) {
		Optional<Country> fetchedCountry = this.iCountryRepository.findById(id);
		if (!fetchedCountry.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		Country newCountry = fetchedCountry.get();
		// newCountry.setName(country.getName());
		newCountry.setCode(country.getCode());

		Country updatedCountry = this.iCountryRepository.save(newCountry);

		return ResponseEntity.ok(updatedCountry);
	}

	/**
	 * Method handling HTTP DELETE requests to delete country details.
	 * 
	 * @return
	 */
	@DeleteMapping("/country/{id}")
	public ResponseEntity<String> deleteCountryById(@PathVariable Long id) {
		Optional<Country> fetchedCountry = this.iCountryRepository.findById(id);
		if (!fetchedCountry.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		this.iCountryRepository.deleteById(id);
		return ResponseEntity.ok("Country with id: " + id + " deleted successfully");
	}

	/**
	 * Method handling HTTP GET requests to fetch a country by code.
	 * 
	 * @return
	 */
	@GetMapping("/country")
	public ResponseEntity<Country> retrieveCountryByCode(@RequestParam("code") String code) {
		Optional<Country> country = this.iCountryRepository.findByCode(code);
		if (!country.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(country.get());
	}

	/**
	 * Method handling HTTP GET requests to fetch a country id and particular
	 * language by id.
	 * 
	 * @return
	 */
	@GetMapping("/country/{cid}/language/{lid}")
	public ResponseEntity<Country> retrieveLanguageById(@PathVariable("cid") Long cid, @PathVariable("lid") Long lid) {
		Optional<Country> country = this.iCountryRepository.getSpecificLanguageOfCountry(cid, lid);
		if (!country.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(country.get());
	}

}
