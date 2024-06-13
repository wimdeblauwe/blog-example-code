package com.wimdeblauwe.examples.transactional_outbox_spring_integration.infrastructure.integration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.PostgresChannelMessageStoreQueryProvider;

@Configuration
public class SpringIntegrationConfiguration {

  private static final String CONCURRENT_METADATA_STORE_PREFIX = "_spring_integration_";

  @Bean
  JdbcChannelMessageStore jdbcChannelMessageStore(
      DataSource dataSource) {
    JdbcChannelMessageStore jdbcChannelMessageStore = new JdbcChannelMessageStore(dataSource);
    jdbcChannelMessageStore.setTablePrefix(CONCURRENT_METADATA_STORE_PREFIX);
    jdbcChannelMessageStore.setChannelMessageStoreQueryProvider(
        new PostgresChannelMessageStoreQueryProvider());
    return jdbcChannelMessageStore;
  }
}
