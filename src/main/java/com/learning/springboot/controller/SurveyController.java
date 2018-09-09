package com.learning.springboot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learning.springboot.model.Question;
import com.learning.springboot.model.Survey;
import com.learning.springboot.repository.SurveyService;

@RestController
class SurveyController {
	@Autowired
	private SurveyService surveyService;

	@GetMapping(path = "/surveys")
	public List<Survey> retrieveQuestions() {
		return surveyService.retrieveAllSurveys();
	}

	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveQuestions(@PathVariable String surveyId) {
		return surveyService.retrieveQuestions(surveyId);
	}

	@GetMapping(path = "/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
		return surveyService.retrieveQuestion(surveyId, questionId);
	}

	@PostMapping(path = "/surveys/{surveyId}/questions")
	ResponseEntity<Object> add(@PathVariable String surveyId, @RequestBody Question question) {
		Question createdTodo = surveyService.addQuestion(surveyId, question);
		if (null == createdTodo) {
			return ResponseEntity.noContent().build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdTodo.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}
