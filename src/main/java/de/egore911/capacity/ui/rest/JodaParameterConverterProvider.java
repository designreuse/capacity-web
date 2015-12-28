package de.egore911.capacity.ui.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.ISODateTimeFormat;

@Provider
public class JodaParameterConverterProvider implements ParamConverterProvider {

	@Override
	public <T> ParamConverter<T> getConverter(Class<T> type, Type genericType, Annotation[] annotations) {
		if (type.equals(DateTime.class)) {
			return (ParamConverter<T>) new DateTimeParamConverter();
		} else if (type.equals(LocalDate.class)) {
			return (ParamConverter<T>) new LocalDateParamConverter();
		} else if (type.equals(LocalTime.class)) {
			return (ParamConverter<T>) new LocalTimeParamConverter();
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
		@Override
		public LocalDate fromString(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return ISODateTimeFormat.date().parseLocalDate(value);
		}

		@Override
		public String toString(LocalDate value) {
			return ISODateTimeFormat.date().print(value);
		}

	}

	private static class LocalTimeParamConverter implements ParamConverter<LocalTime> {
		@Override
		public LocalTime fromString(String value) {
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			return ISODateTimeFormat.time().parseLocalTime(value);
		}

		@Override
		public String toString(LocalTime value) {
			return ISODateTimeFormat.time().print(value);
		}

	}
}