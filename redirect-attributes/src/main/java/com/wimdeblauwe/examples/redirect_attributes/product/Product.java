package com.wimdeblauwe.examples.redirect_attributes.product;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID id, String name, BigDecimal price) {

}
