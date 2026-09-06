package com.wimdeblauwe.greeter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.http.client.ClientHttpRequestInterceptor;

class GreeterAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner =
      new ApplicationContextRunner().withConfiguration(
          AutoConfigurations.of(GreeterAutoConfiguration.class));

  @Test
  void greeterAndInterceptorAreRegisteredWhenSpringWebIsPresent() {
    contextRunner.run(context -> {
      assertThat(context).hasSingleBean(Greeter.class);
      assertThat(context).hasSingleBean(ClientHttpRequestInterceptor.class);
      assertThat(context.getBean(Greeter.class).greet("Wim")).isEqualTo("Hello, Wim!");
    });
  }

  @Test
  void onlyGreeterIsRegisteredWhenSpringWebIsMissing() throws Exception {
    try (HidingClassLoader classLoader =
             HidingClassLoader.defining(GreeterAutoConfiguration.class)
                              .hiding(ClientHttpRequestInterceptor.class)) {
      Class<?> autoConfiguration = classLoader.loadClass(GreeterAutoConfiguration.class.getName());

      new ApplicationContextRunner()
          .withClassLoader(classLoader)
          .withConfiguration(AutoConfigurations.of(autoConfiguration))
          .run(context -> {
            assertThat(context).hasNotFailed();
            assertThat(context).hasSingleBean(Greeter.class);
            assertThat(context).doesNotHaveBean(ClientHttpRequestInterceptor.class);
          });
    }
  }
}
