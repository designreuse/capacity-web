package de.egore911.capacity.ui.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.joda.time.format.ISODateTimeFormat;

@Provider
public class JavaTimeParameterConverterProvider implements ParamConverterProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> type, Type genericType, Annotation[] annotations) {
		if (type.equals(DateTime.class)) {
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

	private static class DateTimeParamConverter implements ParamConverter<DateTime> {
		@Override
		public DateTime fromString(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			try {
				return ISODateTimeFormat.dateTimeNoMillis().parseDateTime(value);
			} catch (IllegalArgumentException e) {
				return ISODateTimeFormat.dateTime().parseDateTime(value);
			}
		}

		@Override
		public String toString(DateTime value) {
			return value.toString();
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