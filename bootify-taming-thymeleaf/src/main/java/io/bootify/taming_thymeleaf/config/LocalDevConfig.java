package io.bootify.taming_thymeleaf.config;

import java.io.File;
import java.io.IOException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;


/**
 * Load Thymeleaf files from the file system during development, without any caching.
 */
@Configuration
@Profile("local")
public class LocalDevConfig {

    public LocalDevConfig(final TemplateEngine templateEngine) throws IOException {
        File sourceRoot = new ClassPathResource("application.properties").getFile().getParentFile();
        while (sourceRoot.listFiles((dir, name) -> name.equals("mvnw")).length != 1) {
            sourceRoot = sourceRoot.getParentFile();
        }
        final FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
        fileTemplateResolver.setPrefix(sourceRoot.getPath() + "/src/main/resources/templates/");
        fileTemplateResolver.setSuffix(".html");
        fileTemplateResolver.setCacheable(false);
        fileTemplateResolver.setCharacterEncoding("UTF-8");
        fileTemplateResolver.setCheckExistence(true);

        templateEngine.setTemplateResolver(fileTemplateResolver);
    }

}
