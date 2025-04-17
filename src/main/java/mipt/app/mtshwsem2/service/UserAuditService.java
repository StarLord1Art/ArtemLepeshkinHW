package mipt.app.mtshwsem2.service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mipt.app.mtshwsem2.configuration.CassandraConfig;
import mipt.app.mtshwsem2.dto.UserAuditDto;
import mipt.app.mtshwsem2.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuditService {
  private final CqlSession session;
  private final CassandraConfig cassandraConfig;

  public void createEventAudit(UUID userId, Action action, String message) {
    if (userId == null || action == null) {
      throw new IllegalArgumentException(
          userId == null ? "UUID must not be null" : "Action must not be null");
    }

    PreparedStatement preparedStatement =
        session.prepare(
            "INSERT INTO "
                + cassandraConfig.getKeyspaceName()
                + ".user_audit (user_id, event_time, event_type, event_details) "
                + "VALUES (?, ?, ?, ?)");

    BoundStatement boundStatement =
        preparedStatement.bind(
            userId, java.time.Instant.now(), action.toString(), message);

    session.execute(boundStatement);
  }

  public List<UserAuditDto> readUserAudit(UUID userId) throws UserNotFoundException {
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

    List<UserAuditDto> userAudits = new ArrayList<>();
    for (Row row : rows) {
      userAudits.add(
          new UserAuditDto(
              row.getUuid("user_id"),
              row.getInstant("event_time"),
              row.getString("event_type"),
              row.getString("event_details")
          )
      );
    }

    return userAudits;
  }
}
