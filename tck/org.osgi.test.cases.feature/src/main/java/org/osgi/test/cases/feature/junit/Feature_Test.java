package org.osgi.test.cases.feature.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.osgi.test.cases.feature.assertj.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.service.feature.BuilderFactory;
import org.osgi.service.feature.Feature;
import org.osgi.service.feature.FeatureBuilder;
import org.osgi.service.feature.FeatureBundle;
import org.osgi.service.feature.FeatureBundleBuilder;
import org.osgi.service.feature.FeatureConfiguration;
import org.osgi.service.feature.FeatureExtension;
import org.osgi.service.feature.FeatureService;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@DisplayName("159.2 Feature")
public class Feature_Test {

	@InjectService(timeout = 200)
	FeatureService fs;

	@Nested
	@DisplayName("Feature API")
	class Feature_API {

		@Test
		@DisplayName("Feature is not null")
		void Feature_isNotNull() throws Exception {

			assertThat(createFeatureBuilderGAV(fs).build()).isNotNull();
		}

		@Test
		@DisplayName("Configurations not null")
		void Feature_Configurations_isNotNull() throws Exception {

			assertThat(createFeatureBuilderGAV(fs).build())
					.hasConfigurationsThat()
					.isEmpty();

		}

		@Test
		@DisplayName("Extensions not null")
		void Feature_Extensions_isNotNull() throws Exception {

			assertThat(createFeatureBuilderGAV(fs).build()).hasExtensionsThat()
					.isEmpty();

		}

		@Test
		@DisplayName("Variables not null")
		void Feature_Variables_isNotNull() throws Exception {

			assertThat(createFeatureBuilderGAV(fs).build()).hasVariablesThat()
					.isEmpty();

		}

		@Test
		@DisplayName("Bundles not null")
		void Feature_Bundles_isNotNull() throws Exception {

			assertThat(createFeatureBuilderGAV(fs).build()).hasBundlesThat()
					.isEmpty();

		}

	}

	// Once created, a Feature is immutable. Its definition cannot be
	// modified
	@Nested
	@DisplayName("a Feature is immutable")
	class Feature_Immutable {

		@Test
		@DisplayName("on bundles")
		void feature_bundles_immutable() throws Exception {
			FeatureBuilder fb = createFeatureBuilderGAV(fs);

			FeatureBundleBuilder fbb = createBundleBuilderGAV(fs);

			FeatureBundle fbundle = fbb.build();
			fb.addBundles(fbundle);

			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				fbundle.getMetadata().put("k", "v");
			});

			Feature feature = fb.build();

			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				feature.getBundles().add(fbundle);
			});

			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				feature.getBundles().add(1, fbundle);
			});

			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				feature.getBundles().get(0).getMetadata().put("k", "v");
			});

		}

		@Test
		@DisplayName("on extensions")
		void feature_extensions_immutable() throws Exception {
			Feature feature = createFeatureBuilderGAV(fs).build();

			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				feature.getExtensions()
						.put("foo", mock(FeatureExtension.class));
			});

		}

		@Test
		@DisplayName("on configurations")
		void feature_configurations_immutable() throws Exception {
			Feature feature = createFeatureBuilderGAV(fs).build();

			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				feature.getConfigurations()
						.put("foo", mock(FeatureConfiguration.class));
			});

		}

		@Test
		@DisplayName("on variables")
		void feature_variables_immutable() throws Exception {
			Feature feature = createFeatureBuilderGAV(fs).build();

			assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> {
				feature.getVariables().put("foo", "bar");
			});

		}

	}

	FeatureBuilder createFeatureBuilderGAV(FeatureService featureService) {
		BuilderFactory builderFactory = featureService.getBuilderFactory();
		FeatureBuilder featureBuilder = builderFactory
				.newFeatureBuilder(fs.getIDfromMavenCoordinates("g:a:v"));
		return featureBuilder;
	}

	FeatureBundleBuilder createBundleBuilderGAV(FeatureService featureService) {
		BuilderFactory builderFactory = featureService.getBuilderFactory();
		FeatureBundleBuilder featureBundleBuilder = builderFactory
				.newBundleBuilder(fs.getIDfromMavenCoordinates("g:a:v"))
				.addMetadata("k", "v");
		return featureBundleBuilder;
	}


}
