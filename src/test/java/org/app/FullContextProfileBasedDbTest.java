package org.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class FullContextProfileBasedDbTest {

  static PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>("postgres:13")
          .withInitScript("../resources/InitialScript.sql")
          .withDatabaseName("testdb")
          .withUsername("testuser")
          .withPassword("testpassword");

  static {
    postgresContainer.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @Test
  public void fullContextProfileBasedDbTest() {
    log.info("Db host: {}", postgresContainer.getHost());
    log.info("Db port: {}", postgresContainer.getFirstMappedPort());
    log.info("PostgreSQL is running at: {}", postgresContainer.getJdbcUrl());
  }
}