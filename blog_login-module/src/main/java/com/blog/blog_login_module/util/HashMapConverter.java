package com.blog.blog_login_module.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String >{
	private final ObjectMapper objectMapper = new ObjectMapper();
	private Logger log = LoggerFactory.getLogger(getClass().getName());
	@Override
	public String convertToDatabaseColumn(Map<String, Object> attribute) {
		if(attribute == null) {
			log.warn("HashMapConverter : convertToDatabaseColumn is being executed");
			return null;
		}
		try {
			log.info("HashMapConverter : convertToDatabaseColumn is being executed");
			return objectMapper.writeValueAsString(attribute);
		} catch (Exception e) {
			System.err.println(e);
		}
		log.warn("HashMapConverter : convertToDatabaseColumn is being executed");
		return null;
	}
	@Override
	public Map<String, Object> convertToEntityAttribute(String dbData) {
		if(dbData==null) {
			log.warn("HashMapConverter : convertToEntityAttribute is being executed");
			return null;
		}
		try {
			log.info("HashMapConverter : convertToEntityAttribute is being executed");
			return objectMapper.readValue(dbData, new TypeReference<Map<String,Object>>() {});
		} catch (Exception e) {
			System.err.println(e);
		}
		log.warn("HashMapConverter : convertToEntityAttribute is being executed");
		return null;
	}
}
