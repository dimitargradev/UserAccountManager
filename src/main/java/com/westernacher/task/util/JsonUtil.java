package com.westernacher.task.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	public static final ObjectMapper MAPPER;

	static {
		MAPPER = new ObjectMapper();
	}

	public static <T> T fromJsonString(String json, Class<T> classOfT) {
		try {
			return MAPPER.readValue(json, classOfT);
		} catch (final IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static <T> T fromJsonString(final byte[] userBytes, Class<T> classOfT) {
		try {
			return MAPPER.readValue(new ByteArrayInputStream(userBytes), classOfT);
		} catch (final IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static <T> byte[] toJsonByteArray(T obj) {
		try {
			return MAPPER.writeValueAsBytes(obj);
		} catch (final JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static <T> String toJsonString(T obj) {
		try {
			return MAPPER.writeValueAsString(obj);
		} catch (final JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static Map<String, Object> fromJsonToMap(String incomingData) {
		final ObjectMapper mapper = MAPPER;
		HashMap<String, Object> propertiesMap = null;
		try {
			propertiesMap = mapper.readValue(incomingData, new TypeReference<Map<String, Object>>() {
			});
		} catch (final IOException e) {
			throw new IllegalStateException(e);
		}

		return propertiesMap;
	}

	public static JsonNode getJsonNodeFromString(String inputData) throws JsonProcessingException, IOException {
		JsonNode rootNode = null;
		rootNode = MAPPER.readTree(inputData);
		return rootNode;
	}

}
