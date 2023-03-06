package com.wimdeblauwe.examples.viewcomponentdemo.product;

import de.tschuehly.thymeleafviewcomponent.ViewComponent;
import de.tschuehly.thymeleafviewcomponent.ViewContext;
import de.tschuehly.thymeleafviewcomponent.ViewProperty;

@ViewComponent
public class ProductViewComponent {

    public ViewContext render(Product product) {
        return new ViewContext(
                ViewProperty.of("product", product)
        );
    }
}
