package de.egore911.capacity.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDateTime;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class VersionExtractorTest {

	private ServletContext mockContext() {
		ServletContext context = Mockito.mock(ServletContext.class);
		Mockito.when(context.getResourceAsStream(Matchers.anyString())).thenAnswer(invocation -> VersionExtractorTest.class.getResourceAsStream((String) invocation.getArguments()[0]));
		return context;
	}

	@Test
	public void testGetMavenVersion() {
		ServletContext context = mockContext();
		String mavenVersion = VersionExtractor.getMavenVersion(context, "de.egore911.capacity", "capacity-test");
		assertThat(mavenVersion, equalTo("0.0.1-SNAPSHOT"));
	}

	@Test
	public void testGetGitRevision() {
		ServletContext context = mockContext();
		String mavenVersion = VersionExtractor.getGitVersion(context, "de.egore911.capacity", "capacity-test");
		assertThat(mavenVersion, equalTo("fe4d7ae"));
	}

	@Test
	public void testGetBuildTimestamp() {
		ServletContext context = mockContext();
		LocalDateTime buildTimestamp = VersionExtractor.getBuildTimestamp(context, "de.egore911.capacity", "capacity-test");
		assertThat(buildTimestamp, equalTo(LocalDateTime.of(2016, 4, 26, 11, 20, 44)));
	}

}
