package com.wimdeblauwe.examples.tcl;


import static org.assertj.core.api.Assertions.assertThat;

import com.wimdeblauwe.examples.tcl.ViteManifestParser.ViteManifest;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.core.io.ByteArrayResource;
import tools.jackson.databind.json.JsonMapper;

@JsonTest
class ViteManifestParserTest {

  @Autowired
  private JsonMapper jsonMapper;

  @Test
  void testParse() throws IOException {
    String manifest = """
        {
          "src/main/resources/static/css/tcl.css": {
            "file": "assets/tcl-css-W1erjkBN.css",
            "name": "tcl-css",
            "names": [
              "tcl-css.css"
            ],
            "src": "src/main/resources/static/css/tcl.css",
            "isEntry": true
          },
          "src/main/resources/static/js/tcl.js": {
            "file": "assets/tcl-js-ezv46uJz.js",
            "name": "tcl-js",
            "src": "src/main/resources/static/js/tcl.js",
            "isEntry": true
          }
        }
        """;
    ViteManifestParser parser = new ViteManifestParser(jsonMapper);
    ViteManifest viteManifest = parser.parse(new ByteArrayResource(manifest.getBytes()));
    assertThat(viteManifest).isNotNull();
    assertThat(viteManifest.getEntry("src/main/resources/static/css/tcl.css")).isNotNull();
    assertThat(viteManifest.getEntry("src/main/resources/static/css/tcl.css").file()).isEqualTo("assets/tcl-css-W1erjkBN.css");
    assertThat(viteManifest.getEntry("src/main/resources/static/js/tcl.js")).isNotNull();
    assertThat(viteManifest.getEntry("src/main/resources/static/js/tcl.js").file()).isEqualTo("assets/tcl-js-ezv46uJz.js");
  }
}