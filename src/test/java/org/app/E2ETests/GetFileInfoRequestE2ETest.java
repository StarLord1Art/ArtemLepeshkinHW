package org.app.E2ETests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" + port + "/files/info/3/1", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().contains("Информация о файле с ID 3 пользователя 1"));
  }
}