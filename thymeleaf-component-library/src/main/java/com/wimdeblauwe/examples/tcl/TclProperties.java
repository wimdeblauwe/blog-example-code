package com.wimdeblauwe.examples.tcl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "tcl")
public record TclProperties(@DefaultValue DevProperties dev) {

  record DevProperties(String viteServerUrl) {

  }
}
