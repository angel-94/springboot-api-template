package com.mars.template.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Angel Ruiz
 * @version 1.0.0
 * date 10/06/25
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperUtils {
	/**
	 * Singleton instance of {@link ModelMapper} used for mapping operations.
	 */
	private static final ModelMapper modelMapper = new ModelMapper();

	static {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
	}

	/**
	 * Maps the source object to a new instance of the specified destination class.
	 * <p>
	 * This method is ideal for creating a new instance of a destination type when transforming
	 * data from one type to another, such as mapping between entity and DTO objects.
	 * </p>
	 *
	 * @param source           the source object to map from
	 * @param destinationClass the class of the destination object
	 * @param <S>              the type of the source object
	 * @param <D>              the type of the destination object
	 * @return a new instance of the destination class with mapped fields from the source object
	 * @throws IllegalArgumentException if source is null or destinationClass is null
	 */
	public static <S, D> D map(S source, Class<D> destinationClass) {
		if (source == null || destinationClass == null) {
			throw new IllegalArgumentException("Source and destinationClass must not be null");
		}
		return modelMapper.map(source, destinationClass);
	}

	/**
	 * Maps the fields of the source object to the provided destination object.
	 * <p>
	 * This method is useful when you want to update an existing instance with data from another object,
	 * instead of creating a new instance.
	 * </p>
	 *
	 * @param source      the source object to map from
	 * @param destination the destination object to map to
	 * @param <S>         the type of the source object
	 * @param <D>         the type of the destination object
	 * @throws IllegalArgumentException if source or destination is null
	 */
	public static <S, D> void map(S source, D destination) {
		if (source == null || destination == null) {
			throw new IllegalArgumentException("Source and destination must not be null");
		}
		modelMapper.map(source, destination);
	}

	/**
	 * Maps a list of source objects to a list of target class objects.
	 * <p>
	 * This method is useful for mapping lists of objects from one type to another, such as mapping a list of entities to a list of DTOs.
	 * </p>
	 *
	 * @param source      the list of source objects to map from
	 * @param targetClass the class of the target objects
	 * @param <S>         the type of the source objects
	 * @param <T>         the type of the target objects
	 * @return a list of mapped target objects
	 * @throws IllegalArgumentException if source or targetClass is null
	 */
	public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
		if (source == null || targetClass == null) {
			throw new IllegalArgumentException("Source and targetClass must not be null");
		}
		return source.stream()
				.map(element -> modelMapper.map(element, targetClass))
				.collect(Collectors.toList());
	}

	/**
	 * Converts a JSON string to an instance of the specified class.
	 *
	 * @param jsonString       the JSON string to convert
	 * @param destinationClass the class to convert the JSON to
	 * @param <D>              the type of the destination object
	 * @return an instance of the destination class populated with data from the JSON string
	 * @throws RuntimeException if jsonString or destinationClass is null
	 */
	public static <D> D fromJson(String jsonString, Class<D> destinationClass) {
		if (jsonString == null || destinationClass == null) {
			throw new IllegalArgumentException("JSON string and destinationClass must not be null");
		}
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.registerModule(new JavaTimeModule());
			return objectMapper.readValue(jsonString, destinationClass);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error converting JSON to object", e);
		}
	}
}
