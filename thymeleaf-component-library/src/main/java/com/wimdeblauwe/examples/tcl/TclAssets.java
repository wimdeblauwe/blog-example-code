package com.wimdeblauwe.examples.tcl;

import com.wimdeblauwe.examples.tcl.ViteManifestParser.ViteManifest;
import java.io.IOException;
import java.util.Objects;
import org.jspecify.annotations.Nullable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

public class TclAssets {

  private static final String MANIFEST_LOCATION = "META-INF/resources/tcl/.vite/manifest.json";
  private static final String BASE_URL = "/tcl/";
  private static final String MANIFEST_KEY_BASE_PATH = "src/main/resources/static/";
  private static final String CSS_MANIFEST_KEY = MANIFEST_KEY_BASE_PATH + "css/tcl.css";
  private static final String JS_MANIFEST_KEY = MANIFEST_KEY_BASE_PATH + "js/tcl.js";

  private boolean devMode;
  private String cssUrl;
  private String jsUrl;
  private String viteClientUrl;

  public TclAssets(@Nullable String viteServerUrl,
      ViteManifestParser viteManifestParser) {
    if (StringUtils.hasText(viteServerUrl)) {
      buildAssetsInDevMode(Objects.requireNonNull(viteServerUrl));
    } else {
      buildAssetsInBuildMode(viteManifestParser);
    }
  }

  public boolean isDevMode() {
    return devMode;
  }

  public String getCssUrl() {
    return cssUrl;
  }

  public String getJsUrl() {
    return jsUrl;
  }

  public String getViteClientUrl() {
    return viteClientUrl;
  }

  private void buildAssetsInDevMode(String viteServerUrl) {
    String base = stripTrailingSlash(viteServerUrl.trim());
    this.devMode = true;
    this.cssUrl = base + "/css/tcl.css";
    this.jsUrl = base + "/js/tcl.js";
    this.viteClientUrl = base + "/@vite/client";
  }

  private void buildAssetsInBuildMode(ViteManifestParser viteManifestParser) {
    this.devMode = false;
    try {
      ClassPathResource resource = new ClassPathResource(MANIFEST_LOCATION);
      if(!resource.exists()) {
        throw new IllegalStateException("Failed to read the Vite manifest at '" + MANIFEST_LOCATION + "'.");
      }
      ViteManifest manifest = viteManifestParser.parse(resource);
      this.cssUrl = BASE_URL + manifest.getEntry(CSS_MANIFEST_KEY).file();
      this.jsUrl = BASE_URL + manifest.getEntry(JS_MANIFEST_KEY).file();
      this.viteClientUrl = null;
    } catch (IOException e) {
      throw new IllegalStateException(
          "Failed to read the Vite manifest at '" + MANIFEST_LOCATION + "'.", e);
    }
  }

  private static String stripTrailingSlash(String url) {
    return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
  }
}
