package com.wimdeblauwe.examples.thymeleafhtmxautherrorhandling;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ChuckNorrisJokesApiClient {

    @GET("/jokes/random")
    Call<ChuckNorrisJoke> randomJoke();
}
