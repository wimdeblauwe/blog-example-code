package com.wimdeblauwe.examples.viewcomponentdemo.product;

import de.tschuehly.thymeleafviewcomponent.ViewComponent;
import de.tschuehly.thymeleafviewcomponent.ViewContext;
import de.tschuehly.thymeleafviewcomponent.ViewProperty;

@ViewComponent
public class ProductListViewComponent {
    private final ProductService service;

    public ProductListViewComponent(ProductService service) {
        this.service = service;
    }

    public ViewContext render() {
        return new ViewContext(
                ViewProperty.of("products", service.getAllProducts())
        );
    }
}
