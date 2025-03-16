package org.app.E2ETests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.app.controller.response.FileInfoResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GetFileInfoRequestE2ETest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void getFileInfoRequestE2ETest() {
    ResponseEntity<FileInfoResponse> response = testRestTemplate.getForEntity("http://localhost:" + port + "/files/info/3/1", FileInfoResponse.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(3, response.getBody().fileId());
    assertEquals("QWERTY.txt", response.getBody().fileName());
    assertEquals("7387gfd", response.getBody().fileOwnerId());
    assertEquals("common", response.getBody().fileAccessLevel());
  }
}