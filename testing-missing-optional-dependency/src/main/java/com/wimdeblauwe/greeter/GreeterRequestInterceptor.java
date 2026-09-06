package com.wimdeblauwe.greeter;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class GreeterRequestInterceptor implements ClientHttpRequestInterceptor {

  private final Greeter greeter;

  public GreeterRequestInterceptor(Greeter greeter) {
    this.greeter = greeter;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                      ClientHttpRequestExecution execution) throws IOException {
    request.getHeaders().add("X-Greeting", greeter.greet("server"));
    return execution.execute(request, body);
  }
}
