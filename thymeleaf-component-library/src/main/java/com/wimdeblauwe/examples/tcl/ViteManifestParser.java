package com.wimdeblauwe.examples.tcl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

public class ViteManifestParser {

  private final JsonMapper jsonMapper;

  public ViteManifestParser(JsonMapper jsonMapper) {
    this.jsonMapper = jsonMapper;
  }

  public ViteManifest parse(Resource resource) throws IOException {
    try (InputStream inputStream = resource.getInputStream()) {
      Map<String, ViteManifestEntry> entries =
          jsonMapper.readValue(inputStream, new TypeReference<>() {
          });
      return new ViteManifest(entries);
    }
  }

  public record ViteManifest(Map<String, ViteManifestEntry> entries) {

    public ViteManifestEntry getEntry(String key) {
      ViteManifestEntry entry = entries.get(key);
      if (entry == null) {
        throw new IllegalArgumentException("No entry found for key %s. Known entries: %s".formatted(key, entries.keySet()));
      }
      return entry;
    }
  }

  public record ViteManifestEntry(String file, String src, boolean isEntry, List<String> css, List<String> imports) {

    public ViteManifestEntry(String file, String src, boolean isEntry, List<String> css, List<String> imports) {
      this.file = file;
      this.src = src;
      this.isEntry = isEntry;
      this.css = css != null ? css : Collections.emptyList();
      this.imports = imports != null ? imports : Collections.emptyList();
    }
  }
}
