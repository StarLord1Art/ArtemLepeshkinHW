package org.app.repository;

import lombok.extern.slf4j.Slf4j;
import org.app.controller.request.DirectoryInfoRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Repository
public class FilesRepository {
  private final RestTemplate restTemplate = new RestTemplate();
  private final WebClient webClient = WebClient.create();

  public String getFileInfo(String fileId, String userId) {
    log.info("Функция получения информации о файле вызвана в репозитории");
    String response = restTemplate.getForObject("https://www.google.com", String.class);
    return "Информация о файле с ID " + fileId + " пользователя " + userId;
  }

  public String getFilesInDirectory(DirectoryInfoRequest directoryInfoRequest) {
    log.info("Функция получения информации о файлах в конкретной папке вызвана в репозитории");
    String response = webClient.get()
        .uri("https://www.google.com")
        .retrieve().bodyToMono(String.class)
        .block();
    return "Файлы из папки " + directoryInfoRequest.path() + " бакета " + directoryInfoRequest.bucket();
  }

  public String getRootDirectories(String bucketName) {
    log.info("Функция для получения папок в корневой директории бакета вызвана в репозитории");
    return "Корневые папки бакета " + bucketName;
  }
}