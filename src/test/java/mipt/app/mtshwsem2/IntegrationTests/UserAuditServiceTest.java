package mipt.app.mtshwsem2.IntegrationTests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;
import mipt.app.mtshwsem2.dto.UserAuditDto;
import mipt.app.mtshwsem2.exception.UserNotFoundException;
import mipt.app.mtshwsem2.service.Action;
import mipt.app.mtshwsem2.service.UserAuditService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@TestPropertySource(
    properties = {
        "spring.cassandra.contact-points=${spring.cassandra.contact-points}",
        "spring.cassandra.port=${spring.cassandra.port}",
        "spring.cassandra.keyspace-name=${spring.cassandra.keyspace-name}",
        "spring.cassandra.local-datacenter=${spring.cassandra.local-datacenter}"
    })
class UserAuditServiceTest {
  @Autowired private UserAuditService userAuditService;

  @Container
  private static final CassandraContainer cassandraContainer =
      new CassandraContainer("cassandra:latest").withExposedPorts(9042);

  private static final UUID uuid = UUID.randomUUID();

  @BeforeAll
  static void setupCassandraConnectionProperties() {
    System.setProperty("spring.cassandra.keyspace-name", "my_keyspace");
    System.setProperty(
        "spring.cassandra.contact-points", cassandraContainer.getContainerIpAddress());
    System.setProperty(
        "spring.cassandra.port", String.valueOf(cassandraContainer.getMappedPort(9042)));

    System.out.println("Cassandra container IP: " + cassandraContainer.getContainerIpAddress());
    System.out.println("Cassandra container port: " + cassandraContainer.getMappedPort(9042));
  }

  @Test
  void shouldSuccessfullyCreateAudit() {
    userAuditService.createEventAudit(uuid, Action.UPDATE, "Test message UPDATE from user");
  }

  @Test
  void shouldFailToCreateAuditWithNullUuid() {
    assertThrows(
        IllegalArgumentException.class,
        () -> userAuditService.createEventAudit(null, Action.DELETE, "Test message DELETE from user"));
  }

  @Test
  void shouldFailToCreateAuditWithNullAction() {
    assertThrows(
        IllegalArgumentException.class, () -> userAuditService.createEventAudit(uuid, null, "Test message DELETE from user"));
  }

  @Test
  void shouldSuccessfullyGetAudit() throws UserNotFoundException {
    List<UserAuditDto> result = userAuditService.readUserAudit(uuid);
    assertEquals(uuid, result.getFirst().userId());
  }

  @Test
  void shouldFailToGetAudit() {
    assertThrows(
        UserNotFoundException.class, () -> userAuditService.readUserAudit(UUID.randomUUID()));
  }
}
