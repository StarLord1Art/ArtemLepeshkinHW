package org.app.ModuleTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.app.aspect.ControllersLoggingAspect;
import org.app.controller.response.FileInfoResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class AspectTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private ControllersLoggingAspect controllersLoggingAspect;

  @Test
  public void shouldIncreaseClassFieldByTwoAspectTest() {
    int fieldValueBeforeExecution = controllersLoggingAspect.getExecutionCount();
    FileInfoResponse response = restTemplate.getForObject("http://localhost:" + port + "/files/info/3/5", FileInfoResponse.class);
    log.info(response.fileName());

    assertEquals(fieldValueBeforeExecution + 2, controllersLoggingAspect.getExecutionCount());
  }
}