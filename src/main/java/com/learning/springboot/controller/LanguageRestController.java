package com.learning.springboot.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learning.springboot.model.Language;
import com.learning.springboot.repository.ILanguageRepository;

@RestController
@RequestMapping("/languages")
public class LanguageRestController {

	private final ILanguageRepository iLanguageRepository;

	/**
	 * Injecting ILanguageRepository into CountryRestController
	 * 
	 * @param iCountryRepository
	 */
	@Autowired
	public LanguageRestController(ILanguageRepository iLanguageRepository) {
		this.iLanguageRepository = iLanguageRepository;
	}

	/**
	 * Method handling HTTP GET requests to fetch all the languages.
	 * 
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Language>> retrieveAllLanguages() {
		List<Language> language = this.iLanguageRepository.findAll();
		if (null == language) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(language);
	}

	/**
	 * Method handling HTTP GET requests to fetch a language by id.
	 * 
	 * @return
	 */
	@GetMapping("/language/{id}")
	public ResponseEntity<Language> retrieveCountryById(@PathVariable Long id) {
		Optional<Language> language = this.iLanguageRepository.findById(id);
		if (!language.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(language.get());
	}

	/**
	 * Method handling HTTP POST requests to save country details
	 * 
	 * @return
	 */

	@PostMapping("/language")
	public ResponseEntity<?> postCountryDetails(@RequestBody Language language) {
		Language savedLanguage = this.iLanguageRepository.save(language);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedLanguage.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

}
