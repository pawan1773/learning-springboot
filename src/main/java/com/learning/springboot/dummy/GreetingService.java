package com.learning.springboot.dummy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.springboot.configuration.TypesafeConfiguration;

@Service
public class GreetingService {

	@Autowired
	TypesafeConfiguration typesafeConfiguration;

	public String greetingsFromService() {
		return "Greetings from service";
	}

	public Map<String, Object> prepareDataFromProperties() {
		Map<String, Object> map = new HashMap<>();
		map.put("message", typesafeConfiguration.getMessage());
		map.put("flag", typesafeConfiguration.getNumber());
		map.put("number", typesafeConfiguration.isFlag());

		return map;
	}
}
