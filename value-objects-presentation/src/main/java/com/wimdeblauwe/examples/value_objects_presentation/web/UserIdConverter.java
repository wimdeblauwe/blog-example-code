package com.wimdeblauwe.examples.value_objects_presentation.web;

import com.wimdeblauwe.examples.value_objects_presentation.UserId;
import org.springframework.boot.jackson.JacksonComponent;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

@JacksonComponent
public class UserIdConverter extends ValueSerializer<UserId> {
  @Override
  public void serialize(UserId userId,
                        JsonGenerator gen,
                        SerializationContext ctxt) throws JacksonException {
    gen.writeString(userId.value().toString());
  }
}
