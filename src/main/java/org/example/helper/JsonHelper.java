package org.example.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * @author zhangyaoxin@yiwise.com
 */
public class JsonHelper {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
				.appendPattern("yyyy-MM-dd'T'HH:mm:ss")
				.appendFraction(java.time.temporal.ChronoField.MILLI_OF_SECOND, 3, 3, true)
				.appendOffset("+HH:mm", "Z")
				.toFormatter();
		objectMapper.registerModule(new JavaTimeModule()
				.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME))
				.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter)));
	}

	public static <T> T string2Object(String jsonStr, Class<T> clazz) throws JsonProcessingException {
		return objectMapper.readValue(jsonStr, clazz);
	}

	public static <T> T string2Object(String jsonStr, TypeReference<T> type) throws JsonProcessingException {
		return objectMapper.readValue(jsonStr, type);
	}

	public static String object2String(Object o) throws JsonProcessingException {
		return objectMapper.writeValueAsString(o);
	}
}
