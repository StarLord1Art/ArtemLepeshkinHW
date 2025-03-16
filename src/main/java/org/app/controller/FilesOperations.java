package org.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.concurrent.CompletableFuture;
import org.app.controller.request.DirectoryInfoRequest;
import org.app.controller.response.FileInfoResponse;
import org.app.exception.NoSuchFileException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Files API", description = "Управление файлами")
public interface FilesOperations {

  @Operation(summary = "Получение информации о файле по ID")
  @ApiResponse(responseCode = "200", description = "Информация о файле получена")
  @GetMapping("/files/info/{fileId}/{userId}")
  ResponseEntity<FileInfoResponse> getFileInfo(@Parameter(description = "ID файла") @PathVariable("fileId") long fileId, @Parameter(description = "ID пользователя") @PathVariable("userId") String userId) throws NoSuchFileException;

  @Operation(summary = "Получение информации о файлах в папке по пути до папки и названию бакета")
  @ApiResponse(responseCode = "200", description = "Информация о файлах в папке получена")
  @GetMapping("/directory/info")
  ResponseEntity<String> getFilesInDirectory(@Parameter(description = "Путь до папки и название бакета") @RequestBody DirectoryInfoRequest directoryInfoRequest);

  @Operation(summary = "Получение корневых папок бакета по его названию")
  @ApiResponse(responseCode = "200", description = "Корневые папки бакета получены")
  @GetMapping("/root/directories/{bucketName}")
  ResponseEntity<CompletableFuture<String>> getRootDirectories(@Parameter(description = "Название бакета") @PathVariable("bucketName") String bucketName);
}