package com.blog_post.post.dto;

public class PostRequest {

	
	private String title;
	
	private String content;

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
		return "PostRequest [title=" + title + ", content=" + content + "]";
	}

	public PostRequest(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}

	public PostRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
