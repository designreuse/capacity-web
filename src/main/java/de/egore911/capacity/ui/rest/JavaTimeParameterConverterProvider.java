package de.egore911.capacity.ui.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

@Provider
public class JavaTimeParameterConverterProvider implements ParamConverterProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> type, Type genericType, Annotation[] annotations) {
		if (type.equals(ZonedDateTime.class)) {
			return (ParamConverter<T>) new DateTimeParamConverter();
		} else if (type.equals(LocalDate.class)) {
			return (ParamConverter<T>) new LocalDateParamConverter();
		} else if (type.equals(LocalTime.class)) {
			return (ParamConverter<T>) new LocalTimeParamConverter();
		} else if (type.equals(LocalDateTime.class)) {
			return (ParamConverter<T>) new LocalDateTimeParamConverter();
		} else {
			return null;
		}
	}

	private static class DateTimeParamConverter implements ParamConverter<ZonedDateTime> {

		private static final DateTimeFormatter FORMATTER_NO_MILLIS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZ");
		private static final DateTimeFormatter FORMATTER_MILLIS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");

		@Override
		public ZonedDateTime fromString(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			try {
				return ZonedDateTime.parse(value, FORMATTER_NO_MILLIS);
			} catch (IllegalArgumentException e) {
				return ZonedDateTime.parse(value, FORMATTER_MILLIS);
			}
		}

		@Override
		public String toString(ZonedDateTime value) {
			return FORMATTER_MILLIS.format(value);
		}

	}

	private static class LocalDateParamConverter implements ParamConverter<LocalDate> {

		private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		@Override
		public LocalDate fromString(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return LocalDate.parse(value, FORMATTER);
		}

		@Override
		public String toString(LocalDate value) {
			return FORMATTER.format(value);
		}

	}

	private static class LocalTimeParamConverter implements ParamConverter<LocalTime> {

		private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSSZZ");
		@Override
		public LocalTime fromString(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return LocalTime.parse(value, FORMATTER);
		}

		@Override
		public String toString(LocalTime value) {
			return FORMATTER.format(value);
		}

	}

	private static class LocalDateTimeParamConverter implements ParamConverter<LocalDateTime> {

		private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
		@Override
		public LocalDateTime fromString(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return LocalDateTime.parse(value, FORMATTER);
		}

		@Override
		public String toString(LocalDateTime value) {
			return FORMATTER.format(value);
		}

	}
}