package com.wimdeblauwe.examples.transactional_outbox_spring_integration_json.infrastructure.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.ChannelMessageStorePreparedStatementSetter;
import org.springframework.integration.jdbc.store.channel.MessageRowMapper;
import org.springframework.integration.jdbc.store.channel.PostgresChannelMessageStoreQueryProvider;
import org.springframework.integration.support.json.JacksonJsonUtils;
import org.springframework.messaging.Message;

@Configuration
public class SpringIntegrationConfiguration {

  private static final String CONCURRENT_METADATA_STORE_PREFIX = "_spring_integration_";
  private final ObjectMapper springIntegrationObjectMapper;

  public SpringIntegrationConfiguration() {
    springIntegrationObjectMapper = JacksonJsonUtils.messagingAwareMapper(
        "com.wimdeblauwe.examples.transactional_outbox_spring_integration_json");
  }

  @Bean
  JdbcChannelMessageStore jdbcChannelMessageStore(
      DataSource dataSource,
      ChannelMessageStorePreparedStatementSetter preparedStatementSetter,
      MessageRowMapper messageRowMapper) {
    JdbcChannelMessageStore jdbcChannelMessageStore = new JdbcChannelMessageStore(dataSource);
    jdbcChannelMessageStore.setTablePrefix(CONCURRENT_METADATA_STORE_PREFIX);
    jdbcChannelMessageStore.setChannelMessageStoreQueryProvider(
        new PostgresChannelMessageStoreQueryProvider());
    jdbcChannelMessageStore.setPreparedStatementSetter(preparedStatementSetter);
    jdbcChannelMessageStore.setMessageRowMapper(messageRowMapper);
    return jdbcChannelMessageStore;
  }

  @Bean
  ChannelMessageStorePreparedStatementSetter channelMessageStorePreparedStatementSetter() {
    return new JacksonMessageStorePreparedStatementSetter(springIntegrationObjectMapper);
  }

  @Bean
  MessageRowMapper messageRowMapper() {
    return new JacksonMessageRowMapper(springIntegrationObjectMapper);
  }

  private static class JacksonMessageStorePreparedStatementSetter extends ChannelMessageStorePreparedStatementSetter {

    private final ObjectMapper objectMapper;

    public JacksonMessageStorePreparedStatementSetter(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, Message<?> requestMessage, Object groupId, String region,
        boolean priorityEnabled) throws SQLException {
      super.setValues(preparedStatement, requestMessage, groupId, region, priorityEnabled);
      try {
        String json = objectMapper.writeValueAsString(requestMessage);
        preparedStatement.setObject(6, json, java.sql.Types.OTHER);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Unable to store message", e);
      }
    }
  }

  private static class JacksonMessageRowMapper extends MessageRowMapper {

    private final ObjectMapper objectMapper;

    public JacksonMessageRowMapper(ObjectMapper objectMapper) {
      super(null, null);
      this.objectMapper = objectMapper;
    }

    @Override
    public Message<?> mapRow(ResultSet rs, int rowNum) throws SQLException {
      try {
        String s = rs.getString(rs.findColumn("MESSAGE_BYTES"));
        return objectMapper.readValue(s, new TypeReference<>() {
        });
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Unable to read message", e);
      }
    }
  }
}
