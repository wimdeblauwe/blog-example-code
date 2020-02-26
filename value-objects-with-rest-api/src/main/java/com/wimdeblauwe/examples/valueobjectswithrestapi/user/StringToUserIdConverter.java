package com.wimdeblauwe.examples.valueobjectswithrestapi.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StringToUserIdConverter implements Converter<String, UserId> {
    @Override
    public UserId convert(@NonNull String s) {
        return new UserId(Long.parseLong(s));
    }
}
