package com.wimdeblauwe.examples.thymeleafhtmxautherrorhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@EnableConfigurationProperties(ChuckNorrisJokesApiClientProperties.class)
public class RestClientConfiguration {

    @Bean
    public ChuckNorrisJokesApiClient jokesApiClient(ObjectMapper objectMapper,
                                                    ChuckNorrisJokesApiClientProperties properties) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(properties.getConnectionTimeout())
                .readTimeout(properties.getReadWriteTimeout());

        return new Retrofit.Builder().client(httpClientBuilder.build())
                                     .baseUrl(properties.getBaseUrl())
                                     .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                                     .build()
                                     .create(ChuckNorrisJokesApiClient.class);

    }

}
