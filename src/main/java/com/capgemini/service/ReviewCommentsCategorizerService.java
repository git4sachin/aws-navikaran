package com.capgemini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.ReviewComment;
import com.capgemini.navikaran.ReviewCommentsCategorizer;
import com.capgemini.utils.CommonUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class ReviewCommentsCategorizerService {

	@Autowired
	private ReviewCommentsCategorizer reviewCommentsCategorizer;

	public String getRReviewCommentsCategories() {
		return getReviewCommentsJson(
				CommonUtilities.convertMapIntoJSonObject(reviewCommentsCategorizer.getJSONConversion()));
	}

	private String getReviewCommentsJson(final List<ReviewComment> reviewComments) {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		@SuppressWarnings("unused")
		String exception = null;
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(reviewComments);
		} catch (Exception ex) {
			ex.printStackTrace();
			exception = ex.getMessage();
		}
		return arrayToJson;
	}
}
