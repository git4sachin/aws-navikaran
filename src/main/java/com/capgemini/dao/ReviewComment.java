package com.capgemini.dao;

public class ReviewComment {

	private Integer count;
	private String category;

	public Integer getCount() {
		return count;
	}

	public ReviewComment(String category, Integer count) {
		super();
		this.count = count;
		this.category = category;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "ReviewComment [count=" + count + ", category=" + category + "]";
	}
}
