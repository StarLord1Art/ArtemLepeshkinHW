package mipt.app.mtshwsem2.service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mipt.app.mtshwsem2.configuration.CassandraConfig;
import mipt.app.mtshwsem2.connector.CassandraConnector;
import mipt.app.mtshwsem2.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuditService {
  private final CassandraConfig cassandraConfig;

  public void initializeKeyspace() {
    CassandraConnector client = new CassandraConnector();
    client.connect(
        cassandraConfig.getContactPoints(),
        cassandraConfig.getPort(),
        cassandraConfig.getLocalDatacenter());

    CqlSession session = client.getSession();

    SimpleStatement statement =
        SimpleStatement.newInstance(
            "CREATE KEYSPACE IF NOT EXISTS "
                + cassandraConfig.getKeyspaceName()
                + " WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};");

    session.execute(statement);

    client.close();
  }

  public void initializeTable() {
    CassandraConnector client = new CassandraConnector();
    client.connect(
        cassandraConfig.getContactPoints(),
        cassandraConfig.getPort(),
        cassandraConfig.getLocalDatacenter());

    CqlSession session = client.getSession();

    SimpleStatement statement =
        SimpleStatement.newInstance(
            "CREATE TABLE IF NOT EXISTS "
                + cassandraConfig.getKeyspaceName()
                + ".user_audit (user_id UUID, event_time TIMESTAMP, event_type TEXT, event_details TEXT, PRIMARY KEY ((user_id), event_time)) "
                + "WITH CLUSTERING ORDER BY (event_time DESC) AND default_time_to_live = 31536000;");

    session.execute(statement);

    client.close();
  }

  public void createEventAudit(UUID userId, Action action) {
    if (userId == null || action == null) {
      throw new IllegalArgumentException(
          userId == null ? "UUID must not be null" : "Action must not be null");
    }

    CassandraConnector client = new CassandraConnector();
    client.connect(
        cassandraConfig.getContactPoints(),
        cassandraConfig.getPort(),
        cassandraConfig.getLocalDatacenter());

    CqlSession session = client.getSession();

    PreparedStatement preparedStatement =
        session.prepare(
            "INSERT INTO "
                + cassandraConfig.getKeyspaceName()
                + ".user_audit (user_id, event_time, event_type, event_details) "
                + "VALUES (?, ?, ?, ?)");

    BoundStatement boundStatement =
        preparedStatement.bind(
            userId, java.time.Instant.now(), action.toString(), "User UPDATE from IP 192.168.1.1");

    session.execute(boundStatement);

    client.close();
  }

  public String readUserAudit(UUID userId) throws UserNotFoundException {
    CassandraConnector client = new CassandraConnector();
    client.connect(
        cassandraConfig.getContactPoints(),
        cassandraConfig.getPort(),
        cassandraConfig.getLocalDatacenter());

    CqlSession session = client.getSession();

    PreparedStatement statement =
        session.prepare(
            "SELECT * FROM "
                + cassandraConfig.getKeyspaceName()
                + ".user_audit WHERE user_id = ?;");
    BoundStatement boundStatement = statement.bind(userId);

    ResultSet rows = session.execute(boundStatement);

    if (!rows.iterator().hasNext()) {
      throw new UserNotFoundException("User with UUID " + userId + " wasn't found");
    }

    client.close();

    return rows.one().getString("event_details");
  }
}
