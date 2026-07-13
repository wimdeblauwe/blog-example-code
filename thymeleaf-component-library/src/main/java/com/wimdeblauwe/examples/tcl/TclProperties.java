package com.wimdeblauwe.examples.tcl;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tcl")
public record TclProperties(DevProperties dev) {

  record DevProperties(String viteServerUrl) {

  }
}
