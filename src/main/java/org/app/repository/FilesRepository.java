package org.app.repository;

import org.app.controller.request.DirectoryInfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FilesRepository {
  private static final Logger log = LoggerFactory.getLogger(FilesRepository.class);

  public String getFileInfo(String fileId, String userId) {
    log.info("Функция получения информации о файле вызвана в репозитории");
    return "Информация о файле с ID " + fileId + " пользователя " + userId;
  }

  public String getFilesInDirectory(DirectoryInfoRequest directoryInfoRequest) {
    log.info("Функция получения информации о файлах в конкретной папке вызвана в репозитории");
    return "Файлы из папки " + directoryInfoRequest.path() + " бакета " + directoryInfoRequest.bucket();
  }

  public String getRootDirectories(String bucketName) {
    log.info("Функция для получения папок в корневой директории бакета вызвана в репозитории");
    return "Корневые папки бакета " + bucketName;
  }
}