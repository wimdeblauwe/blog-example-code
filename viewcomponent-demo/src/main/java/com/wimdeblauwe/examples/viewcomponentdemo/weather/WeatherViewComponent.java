package com.wimdeblauwe.examples.viewcomponentdemo.weather;

import de.tschuehly.thymeleafviewcomponent.ViewComponent;
import de.tschuehly.thymeleafviewcomponent.ViewContext;
import de.tschuehly.thymeleafviewcomponent.ViewProperty;

@ViewComponent
public class WeatherViewComponent {
    public ViewContext render() {
        return new ViewContext(ViewProperty.of("test", "something"));
    }
}
