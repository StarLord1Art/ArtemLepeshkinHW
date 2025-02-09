package org.app.service;

import org.app.controller.request.DirectoryInfoRequest;
import org.app.repository.FilesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FilesService {
  private static final Logger log = LoggerFactory.getLogger(FilesService.class);
  private final FilesRepository filesRepository;

  public FilesService(FilesRepository filesRepository) {
    this.filesRepository = filesRepository;
  }

  public String getFileInfo(String fileId, String userId) {
    log.info("Функция получения информации о файле вызвана в сервисе");
    return filesRepository.getFileInfo(fileId, userId);
  }

  public String getFilesInDirectory(DirectoryInfoRequest directoryInfoRequest) {
    log.info("Функция получения информации о файлах в конкретной папке вызвана в сервисе");
    return filesRepository.getFilesInDirectory(directoryInfoRequest);
  }

  public String getRootDirectories(String bucketName) {
    log.info("Функция для получения папок в корневой директории бакета вызвана в сервисе");
    return filesRepository.getRootDirectories(bucketName);
  }
}