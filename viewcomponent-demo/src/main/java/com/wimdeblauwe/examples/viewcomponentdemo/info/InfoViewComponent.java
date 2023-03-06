package com.wimdeblauwe.examples.viewcomponentdemo.info;

import de.tschuehly.thymeleafviewcomponent.ViewComponent;
import de.tschuehly.thymeleafviewcomponent.ViewContext;
import de.tschuehly.thymeleafviewcomponent.ViewProperty;

@ViewComponent
public class InfoViewComponent {
    public ViewContext render() {
        return new ViewContext(
                ViewProperty.of("title", "ViewConf"),
                ViewProperty.of("message", "The conference for fullstack Java development")
        );
    }
}
