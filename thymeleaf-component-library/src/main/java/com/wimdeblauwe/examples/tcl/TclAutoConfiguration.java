package com.wimdeblauwe.examples.tcl;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import tools.jackson.databind.json.JsonMapper;

@AutoConfiguration
@EnableConfigurationProperties(TclProperties.class)
public class TclAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public TclAssets tclAssets(TclProperties properties,
      ViteManifestParser viteManifestParser) {
    return new TclAssets(properties.dev().viteServerUrl(), viteManifestParser);
  }

  @Bean
  @ConditionalOnMissingBean
  public ViteManifestParser viteManifestParser(JsonMapper jsonMapper) {
    return new ViteManifestParser(jsonMapper);
  }

  @Bean
  @ConditionalOnMissingBean
  public TclDialect tclDialect() {
    return new TclDialect();
  }

  @Bean
  @ConditionalOnProperty("tcl.dev.templates-path")
  public FileTemplateResolver tclDevTemplateResolver(TclProperties properties) {
    FileTemplateResolver resolver = new FileTemplateResolver();
    resolver.setPrefix(properties.dev().templatesPath());
    resolver.setSuffix(".html");
    resolver.setTemplateMode(TemplateMode.HTML);
    resolver.setCharacterEncoding("UTF-8");
    resolver.setCacheable(false);
    resolver.setCheckExistence(true);
    resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return resolver;
  }
}
