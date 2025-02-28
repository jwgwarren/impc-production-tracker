package org.gentar.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

/** Collection of utility methods to work with JSON strings. */
public class JsonHelper
{

  private static ObjectMapper objectMapper;

  static {
    objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.registerModule(new JavaTimeModule());
  }

  private JsonHelper() {}

  /**
   * Parses the given JSON string into a Java object using a standard Jackson mapper.
   *
   * @param json the JSON string to parse.
   * @param toClass class of the target object.
   * @param <T> type of the target object.
   * @return the Java object the JSON string was mapped into.
   * @throws IOException if the JSON string could not be parsed into an object of the given target
   *     type.
   */
  public static <T> T fromJson(String json, Class<T> toClass) throws IOException {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper.readValue(json, toClass);
  }

  /**
   * Parses the given JSON string into a Java object using a standard Jackson mapper. This method
   * allows to specify a {@link TypeReference} that describes the class to parse and thus supports
   * parsing generic types.
   *
   * @param json the JSON string to parse.
   * @param typeReference a {@link TypeReference} object that describes the class to parse.
   * @param <T> type of the target object.
   * @return the Java object the JSON string was mapped into.
   * @throws IOException if the JSON string could not be parsed into an object of the given target
   *     type.
   */
  public static <T> T fromJson(String json, TypeReference<T> typeReference) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper.readValue(json, typeReference);
  }

  /** Maps the given object to JSON using a standard Jackson mapper. */
  public static <T> String toJson(T object) throws JsonProcessingException
  {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }

}
