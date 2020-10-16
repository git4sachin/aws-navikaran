package com.capgemini.navikaran.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {
	
	@RequestMapping("/")
	public String home() {
		return "<h1>Welcome to team Navikaran's web application !</h1>";
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "<h1>Hello from Sachin..!</h1>";
	}
	

}
