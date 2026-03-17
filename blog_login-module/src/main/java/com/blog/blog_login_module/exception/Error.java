package com.blog.blog_login_module.exception;

import lombok.Data;
@Data
public class Error {
	private int status;
	private long timestamp;
	private String message;
}
