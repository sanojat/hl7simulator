package com.hl7.learn.simulator.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 1013744
 *
 */
public final class JsonUtil {

	/**
	 * 
	 */
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String getString(Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}

	/**
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public static <T> T getObject(String jsonStr, Class<T> clazz) throws JsonMappingException, JsonProcessingException {
		return mapper.readValue(jsonStr, clazz);
	}

	/**
	 * @param <T>
	 * @param file
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T getObject(File file, Class<T> clazz) throws IOException {
		return mapper.readValue(file, clazz);
	}
}
