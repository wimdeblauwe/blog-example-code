package com.wimdeblauwe.examples.valueobjectswithrestapiuuid.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToUserIdConverter implements Converter<String, UserId>
{
	@Override
	public UserId convert( @NonNull String uuid )
	{
		return new UserId( UUID.fromString( uuid ) );
	}
}
