package com.wimdeblauwe.examples.spendleaf.expense.repository;

import com.wimdeblauwe.examples.spendleaf.expense.Amount;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;

@Converter(autoApply = true)
class AmountAttributeConverter implements AttributeConverter<Amount, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(Amount attribute) {
        return attribute.value();
    }

    @Override
    public Amount convertToEntityAttribute(BigDecimal dbData) {
        return new Amount(dbData);
    }
}
