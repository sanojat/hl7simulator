package com.hl7.learn.simulator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/test")
	public String getEmployeeByID() {
		return "IT is working";
	}
}
