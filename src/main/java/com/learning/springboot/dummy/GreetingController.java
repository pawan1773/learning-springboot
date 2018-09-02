package com.learning.springboot.dummy;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	@Autowired
	GreetingService greetingService;

	// http://localhost:8081/dummy
	@GetMapping(path = "/greetings")
	public String greetings() {
		return "Greetings from controller";
	}

	// http://localhost:8081/greetings/service
	@GetMapping(path = "/greetings/service")
	public String greetingsFromService() {
		return greetingService.greetingsFromService();
	}

	// http://localhost:8081/greetings/welcome
	@GetMapping(path = "/greetings/{message}")
	public String getMessageFromPathVariable(@PathVariable("message") String message) {
		return "Greetings from path variable: " + message;
	}

	// http://localhost:8081/greetings-from-request-param/?param=hello
	@GetMapping(path = "/greetings-from-request-param")
	public String getMessageFromRequestParam(@RequestParam("param") String param) {
		return "Greetings from request param: " + param;
	}

	@GetMapping(path = "/properties", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getValueFromProperties() {
		return greetingService.prepareDataFromProperties();
	}

}
