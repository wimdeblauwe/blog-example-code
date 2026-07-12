package com.wimdeblauwe.examples.tcl;

import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(TclProperties.class)
public class TclAutoConfiguration {

//  @Bean
//  @ConditionalOnMissingBean
//  public TclAssets tclAssets(TclProperties properties) {
//    return new TclAssets(properties.dev().viteServerUrl());
//  }
}
