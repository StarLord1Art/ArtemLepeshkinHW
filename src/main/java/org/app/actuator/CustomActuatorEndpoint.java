package org.app.actuator;

import java.util.UUID;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "custom")
public class CustomActuatorEndpoint {

  @ReadOperation
  public UUID customInfo() {
    return UUID.randomUUID();
  }
}