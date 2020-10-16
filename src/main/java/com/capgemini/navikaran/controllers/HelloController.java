package com.capgemini.navikaran.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/hello")
	public String hello() {
		return "<h1>Hello from team Navikaran..!</h1>";
	}
	
	@RequestMapping("/")
	public String home() {
		return "<h1>Welcome to team Navikaran's web application !</h1>";
	}

}
