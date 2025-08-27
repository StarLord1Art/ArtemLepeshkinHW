package org.app.IntegrationTests;

import static org.junit.Assert.assertEquals;

import org.app.controller.response.FileInfoResponse;
import org.app.repository.FilesRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FilesRepositoryTest {

  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES::getUsername);
    registry.add("spring.datasource.password", POSTGRES::getPassword);
  }

  @Autowired
  private FilesRepository filesRepository;

  @Test
  public void getFileInfoRepositoryTest() {
    FileInfoResponse fileInfoResponse = filesRepository.findFileByFileId(3);
    assertEquals(3, fileInfoResponse.fileId());
    assertEquals("QWERTY.txt", fileInfoResponse.fileName());
    assertEquals("7387gfd", fileInfoResponse.fileOwnerId());
    assertEquals("10/08/2023", fileInfoResponse.fileCreationDate());
    assertEquals("25/09/2025", fileInfoResponse.fileLastModifiedDate());
    assertEquals("common", fileInfoResponse.fileAccessLevel());
    assertEquals("study", fileInfoResponse.fileTags());
  }
}