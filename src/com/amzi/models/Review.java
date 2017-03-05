package com.amzi.models;

public class Review {
	private String body, dateCreated, authorUsername;
	private int restaurantId, authorId;

	public String getBody() {
		return body;
	}

	public Review(String body, String dateCreated, String authorName, int restaurantId, int authorId) {
		super();
		this.body = body;
		this.dateCreated = dateCreated;
		this.authorUsername = authorName;
		this.restaurantId = restaurantId;
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorUsername;
	}

	public void setAuthorName(String authorName) {
		this.authorUsername = authorName;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

}
