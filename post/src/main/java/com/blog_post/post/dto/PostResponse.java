package com.blog_post.post.dto;

public class PostResponse {

	
	private long id;
	private String userId;
	private String title;
	private String content;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "PostResponse [id=" + id + ", userId=" + userId + ", title=" + title + ", content=" + content + "]";
	}
	public PostResponse(long id, String userId, String title, String content) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.content = content;
	}
	public PostResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
