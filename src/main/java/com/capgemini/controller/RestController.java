package com.capgemini.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.service.ReviewCommentsCategorizerService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/restfull-service")
public class RestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ReviewCommentsCategorizerService reviewCommentsCategorizerService;

	@RequestMapping(value = "/reviewCommentsCaregory.json", method = RequestMethod.GET)
	public @ResponseBody String getReviewCommentsCaregory(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return reviewCommentsCategorizerService.getRReviewCommentsCategories();
	}

}