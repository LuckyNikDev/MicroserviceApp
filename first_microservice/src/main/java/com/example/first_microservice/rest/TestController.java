package com.example.first_microservice.rest;


import com.example.first_microservice.feignClient.SecondMicroClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
	private final SecondMicroClient secondMicroClient;

	@GetMapping("/hello")
	public String getPhrase() {
		return "You are in first_microservice!";
	}

	@GetMapping("/feign")
	public String getPhraseFromSecondMicro() {
		return secondMicroClient.getPhrase();
	}
}
