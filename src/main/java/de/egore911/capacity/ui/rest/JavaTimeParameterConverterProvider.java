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

		@Override
		public ZonedDateTime fromString(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return ZonedDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		}

		@Override
		public String toString(ZonedDateTime value) {
			return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value);
		}

	}

	private static class LocalDateParamConverter implements ParamConverter<LocalDate> {

		@Override
		public LocalDate fromString(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
		}

		@Override
		public String toString(LocalDate value) {
			return DateTimeFormatter.ISO_LOCAL_DATE.format(value);
		}

	}

	private static class LocalTimeParamConverter implements ParamConverter<LocalTime> {

		@Override
		public LocalTime fromString(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return LocalTime.parse(value, DateTimeFormatter.ISO_OFFSET_TIME);
		}

		@Override
		public String toString(LocalTime value) {
			return DateTimeFormatter.ISO_OFFSET_TIME.format(value);
		}

	}

	private static class LocalDateTimeParamConverter implements ParamConverter<LocalDateTime> {

		@Override
		public LocalDateTime fromString(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return LocalDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		}

		@Override
		public String toString(LocalDateTime value) {
			return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value);
		}

	}
}