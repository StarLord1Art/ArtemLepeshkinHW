package org.app.controller;

import org.app.controller.request.DirectoryInfoRequest;
import org.app.service.FilesService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class FilesController implements FilesOperations {
  private final FilesService filesService;

  public FilesController(FilesService filesService) {
    this.filesService = filesService;
  }

  @Override
  public ResponseEntity<String> getFileInfo(String fileId, String userId) {
    return ResponseEntity.ok(filesService.getFileInfo(fileId, userId));
  }

  @Override
  public ResponseEntity<String> getFilesInDirectory(DirectoryInfoRequest directoryInfoRequest) {
    return ResponseEntity.ok(filesService.getFilesInDirectory(directoryInfoRequest));
  }

  @Override
  public ResponseEntity<String> getRootDirectories(String bucketName) {
    return ResponseEntity.ok(filesService.getRootDirectories(bucketName));
  }
}