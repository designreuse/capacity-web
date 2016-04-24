package de.egore911.capacity.util;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;

public class VersionExtractor {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

	@Nullable
	private static String load(@Nonnull ServletContext context, @Nonnull String groupId,
			@Nonnull String artifactId, @Nonnull String file, @Nonnull String property) {
		Properties properties = new Properties();
		try (InputStream pomPropertiesStream = context
				.getResourceAsStream("/META-INF/maven/" + groupId + "/" + artifactId + "/" + file)) {
			if (pomPropertiesStream != null) {
				properties.load(pomPropertiesStream);
			}
		} catch (IOException | NullPointerException e) {
			return null;
		}
		String version = properties.getProperty(property);
		if (StringUtils.isEmpty(version)) {
			return null;
		}
		return version;
	}

	@Nonnull
	public static String getMavenVersion(@Nonnull ServletContext context, @Nonnull String groupId,
			@Nonnull String artifactId) {
		String version = load(context, groupId, artifactId, "pom.properties", "version");
		if (StringUtils.isEmpty(version)) {
			version = "?";
		}
		return version;
	}

	@Nonnull
	public static String getGitVersion(@Nonnull ServletContext context, @Nonnull String groupId,
			@Nonnull String artifactId) {
		String version = load(context, groupId, artifactId, "git.properties", "git.commit.id.abbrev");
		if (StringUtils.isEmpty(version)) {
			version = "";
		}
		return version;
	}

	@Nullable
	public static LocalDateTime getBuildTimestamp(@Nonnull ServletContext context, @Nonnull String groupId,
			@Nonnull String artifactId) {
		return toDate(load(context, groupId, artifactId, "git.properties", "git.build.time"));
	}

	@Nullable
	private static LocalDateTime toDate(@Nullable String string) {
		if (string == null) {
			return null;
		}
		try {
			return LocalDateTime.parse(string, FORMATTER);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}
