package com.wimdeblauwe.examples.value_objects_presentation.repository;

import com.wimdeblauwe.examples.value_objects_presentation.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<Email, String> {
  @Override
  public String convertToDatabaseColumn(Email email) {
    return email != null ? email.value() : null;
  }

  @Override
  public Email convertToEntityAttribute(String value) {
    return value != null ? new Email(value) : null;
  }
}
