package com.example.first_microservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "second", url = "http://localhost:8082/")
public interface SecondMicroClient {
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	String getPhrase();
}
