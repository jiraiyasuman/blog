package com.blog.blog_login_module.handler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GenericResponse {

	private HttpStatus responseStatus;
	
	private String status;
	
	private String message;
	
	private long localDateTime;
	
	private Object data;
	
	public ResponseEntity<?> create(){
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("status", status);
		map.put("message", message);
		localDateTime = System.currentTimeMillis();
		map.put("timestamp", localDateTime);
		if(!ObjectUtils.isEmpty(data)) {
			map.put("data", data);
		}
		return new ResponseEntity<>(map,responseStatus);
	}
}
