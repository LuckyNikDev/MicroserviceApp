package com.example.second_microservice.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/hello")
	public String getPhrase() {
		return "You are in second_controller!";
	}
}
