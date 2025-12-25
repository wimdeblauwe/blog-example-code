package com.wimdeblauwe.examples.value_objects_presentation.web;

import com.wimdeblauwe.examples.value_objects_presentation.Money;

public record CreateProductRequest(String name, Money price) {
}
