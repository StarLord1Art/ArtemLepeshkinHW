package org.app.service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.controller.request.DirectoryInfoRequest;
import org.app.exception.NoSuchFileException;
import org.app.repository.FilesRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilesService {
  private final FilesRepository filesRepository;
  private final Set<String> processedDirectories = ConcurrentHashMap.newKeySet();

  // At Least Once
  @Cacheable(cacheNames = {"receivedFileInfo"}, key = "{#fileId}")
  @Retryable(value = NoSuchFileException.class, maxAttempts = 5, backoff = @Backoff(delay = 10000))
  public String getFileInfo(String fileId, String userId) throws NoSuchFileException {
    log.info("Функция получения информации о файле вызвана в сервисе");

    if (Math.random() < 0.5) {
      throw new NoSuchFileException("Файл не найден");
    }

    return filesRepository.getFileInfo(fileId, userId);
  }

  // Exactly Once
  @Cacheable(cacheNames = {"receivedFilesInDirectory"}, key = "{#directoryInfoRequest.path()}")
  public String getFilesInDirectory(DirectoryInfoRequest directoryInfoRequest) {
    log.info("Функция получения информации о файлах в конкретной папке вызвана в сервисе");

    if (!processedDirectories.add(directoryInfoRequest.path())) {
      log.info("Запрос на получение файлов из папки {} уже был обработан", directoryInfoRequest.path());
      return "";
    }

    return filesRepository.getFilesInDirectory(directoryInfoRequest);
  }

  @Async
  public CompletableFuture<String> getRootDirectories(String bucketName) {
    log.info("Функция для получения папок в корневой директории бакета вызвана в сервисе");
    return CompletableFuture.completedFuture(filesRepository.getRootDirectories(bucketName));
  }
}