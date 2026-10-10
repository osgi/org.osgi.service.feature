package org.osgi.test.cases.feature.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.osgi.test.cases.feature.assertj.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.osgi.service.feature.FeatureService;
import org.osgi.service.feature.ID;
import org.osgi.test.cases.feature.junit.FeatureServiceTest.ReadFeatureTest.ReadFeatureIDTest;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(ServiceExtension.class)
@ExtendWith(BundleContextExtension.class)
public class FeatureIDTest {

	// toString may use type and classifier
	// getType may return an Optional<String>
	// getClassifier may return an Optional<String>

	private static final String	G_A_V		= "g:a:v";
	private static final String	G_A_T_V		= "g:a:t:v";
	private static final String	G_A_T_C_V	= "g:a:t:c:v";

	@InjectService
	FeatureService				fs;

	@Test
	void getID3() throws Exception {

		ID id = fs.getID("g", "a", "v");
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasEmptyType()
				.hasEmptyClassifier()
				.hasToString(G_A_V);

	}

	@Test
	void getID4_gavt() throws Exception {

		ID id = fs.getID("g", "a", "v", "t");
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasType("t")
				.hasEmptyClassifier()
				.hasToString(G_A_T_V);
	}

	@Test
	void getID5_gavtc() throws Exception {

		ID id = fs.getID("g", "a", "v", "t", "c");
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasType("t")
				.hasClassifier("c")
				.hasToString(G_A_T_C_V);

	}

	@Test
	void getID3_shouldFail() throws Exception {

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID(null, null, null));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("", "", ""));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", null));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", ""));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", null, "v"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "", "v"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID(null, "a", "v"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("", "a", "v"));

	}

	@Test
	void getID4_shouldFail() throws Exception {
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID(null, null, null, null));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("", "", "", ""));

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "v", ""));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "v", null));// <--

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "", "t"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", null, "t"));

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "", "v", "t"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", null, "v", "t"));

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("", "a", "v", "t"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID(null, "a", "v", "t"));
	}

	@Test
	void getID5_shouldFail() throws Exception {
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID(null, null, null, null, null));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("", "", "", "", ""));

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "v", "t", ""));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "v", "t", null));// <--

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "v", "", "c"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "v", null, "c"));

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "", "t", "c"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", null, "t", "c"));

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "", "v", "t", "c"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", null, "v", "t", "c"));

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("", "a", "v", "t", "c"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID(null, "a", "v", "t", "c"));

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "v", null, null));// <--

	}

	@Test
	void getIDX_shouldFail_NOT_SPECIFIED() throws Exception {

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "v", "t", "c:c"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "v", "t:t", "c"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a", "v:v", "t", "c"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g", "a:a", "v", "t", "c"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID("g:g", "a", "v", "t", "c"));
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getID(":", ":", ":", ":", ":"));

	}

	void getIDfromMavenCoordinate_null_shouldFail() throws Exception {
		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getIDfromMavenCoordinates(null));
	}

	// same source as
	static Class< ? > here = ReadFeatureIDTest.class;
	@ParameterizedTest
	@ValueSource(strings = {
			":", "::", ":::", "::::", ":::::", //
			// " : ", " : : ", " : : : ", " : : : : ", " : : : : : ",
			"g", "g:", "g::", "g:::", "g::::", //
			"g:a", "g:a:", "g:a::", "g:a:::", //
			"g:a:v:", "g:a:v::", //
			"g:a:t:c:", //
			"g:a:t::v", //
			"g::t:c:v", //
			"g:::t:v", //
			":a:t:c:v", //
			"g:a:t:c:v:", //
			"g:a:t:c:v:foo", //
	})
	void getIDfromMavenCoordinates_shouldFail(String wrongCoordinates)
			throws Exception {

		assertThatExceptionOfType(Throwable.class).isThrownBy(() -> fs.getIDfromMavenCoordinates(wrongCoordinates));


	}

	@Test
	void getIDfromMavenCoordinates_Gav() throws Exception {
		ID id = fs.getIDfromMavenCoordinates(G_A_V);
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasEmptyType()
				.hasEmptyClassifier()
				.hasToString(G_A_V);

	}

	@Test
	void getIDfromMavenCoordinates_gavt() throws Exception {
		ID id = fs.getIDfromMavenCoordinates(G_A_T_V);
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasType("t")
				.hasEmptyClassifier()
				.hasToString(G_A_T_V);
	}

	@Test
	void getIDfromMavenCoordinates_gavtc() throws Exception {
		ID id = fs.getIDfromMavenCoordinates(G_A_T_C_V);
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasType("t")
				.hasClassifier("c")
				.hasToString(G_A_T_C_V);
	}
}
