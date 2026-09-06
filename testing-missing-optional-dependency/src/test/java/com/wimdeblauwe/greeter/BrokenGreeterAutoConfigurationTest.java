package com.wimdeblauwe.greeter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * Shows that {@link FilteredClassLoader} does not detect the problem in
 * {@link BrokenGreeterAutoConfiguration}, while {@link HidingClassLoader} does.
 */
class BrokenGreeterAutoConfigurationTest {

  @Test
  void filteredClassLoaderDoesNotDetectTheProblem() {
    new ApplicationContextRunner()
        .withClassLoader(new FilteredClassLoader(ClientHttpRequestInterceptor.class))
        .withConfiguration(AutoConfigurations.of(BrokenGreeterAutoConfiguration.class))
        .run(context -> assertThat(context).hasNotFailed());
  }

  @Test
  void hidingClassLoaderDetectsTheProblem() throws Exception {
    try (HidingClassLoader classLoader =
             HidingClassLoader.defining(BrokenGreeterAutoConfiguration.class)
                              .hiding(ClientHttpRequestInterceptor.class)) {
      Class<?> autoConfiguration =
          classLoader.loadClass(BrokenGreeterAutoConfiguration.class.getName());

      new ApplicationContextRunner()
          .withClassLoader(classLoader)
          .withConfiguration(AutoConfigurations.of(autoConfiguration))
          .run(context -> assertThat(context).getFailure()
                                             .rootCause()
                                             .isInstanceOf(ClassNotFoundException.class)
                                             .hasMessageContaining(
                                                 ClientHttpRequestInterceptor.class.getName()));
    }
  }
}
