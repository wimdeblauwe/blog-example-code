package com.wimdeblauwe.examples.value_objects_presentation.web;


import com.wimdeblauwe.examples.value_objects_presentation.Money;
import com.wimdeblauwe.examples.value_objects_presentation.Product;
import com.wimdeblauwe.examples.value_objects_presentation.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

  @MockitoBean
  private ProductRepository productRepository;

  @Autowired
  private MockMvc mockMvc;

  private RestTestClient client;

  @BeforeEach
  void setUp() {
    client = RestTestClient.bindTo(mockMvc)
        .build();
  }

  @Test
  void testGetProducts() {

    List<Product> products = List.of(new Product(1L, "Product One", Money.ofEuro(10)),
                                     new Product(2L, "Product Two", Money.ofEuro(20)));
    when(productRepository.findAll(any(Pageable.class)))
        .thenReturn(new PageImpl<>(products));

    client.get().uri("/api/products").exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.length()").isEqualTo(3);
  }

  @Test
  void testCreateProduct() {

    when(productRepository.save(any(Product.class)))
        .thenReturn(new Product(3L, "Product Three", Money.ofEuro(30)));

    client.post().uri("/api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .body("""
                  {
                  "name": "Product Three",
                  "price": { "amount": 30, "currency": "EUR" }
                  }
                  """)
        .exchange()
        .expectStatus().isCreated();
  }
}