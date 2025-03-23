package mipt.app.mtshwsem2.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.cassandra")
@Setter
@Getter
public class CassandraConfig {
  private String contactPoints;
  private int port;
  private String keyspaceName;
  private String localDatacenter;
}
