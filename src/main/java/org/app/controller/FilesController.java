package org.app.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.app.controller.request.DirectoryInfoRequest;
import org.app.controller.response.FileInfoResponse;
import org.app.exception.NoSuchFileException;
import org.app.service.FilesService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@RateLimiter(name = "apiRateLimiter")
@CircuitBreaker(name = "apiCircuitBreaker")
public class FilesController implements FilesOperations {
  private final FilesService filesService;

  @Override
  public ResponseEntity<FileInfoResponse> getFileInfo(long fileId, String userId)
      throws NoSuchFileException {
    return ResponseEntity.ok(filesService.getFileInfo(fileId, userId));
  }

  @Override
  public ResponseEntity<String> getFilesInDirectory(DirectoryInfoRequest directoryInfoRequest) {
    return ResponseEntity.ok(filesService.getFilesInDirectory(directoryInfoRequest));
  }

  @Override
  public ResponseEntity<CompletableFuture<String>> getRootDirectories(String bucketName) {
    return ResponseEntity.ok(filesService.getRootDirectories(bucketName));
  }
}