package com.capgemini.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppErrorController implements ErrorController {

	private static String errorString  = "<font color=\"red\"> No URL mapping found.</font>";
	
	@Override
	@RequestMapping("/error")
	@ResponseBody
	public String getErrorPath() {
		return errorString;
	}

}
