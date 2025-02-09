package org.app.controller;

import org.app.controller.request.DirectoryInfoRequest;
import org.app.service.FilesService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FilesController {
  private final FilesService filesService;

  public FilesController(FilesService filesService) {
    this.filesService = filesService;
  }

  @GetMapping("/files/info/{fileId}/{userId}")
  public ResponseEntity<String> getFileInfo(@PathVariable("fileId") String fileId, @PathVariable("userId") String userId) {
    return ResponseEntity.ok(filesService.getFileInfo(fileId, userId));
  }

  @GetMapping("/directory/info")
  public ResponseEntity<String> getFilesInDirectory(@RequestBody DirectoryInfoRequest directoryInfoRequest) {
    return ResponseEntity.ok(filesService.getFilesInDirectory(directoryInfoRequest));
  }

  @GetMapping("/root/directories/{bucketName}")
  public ResponseEntity<String> getRootDirectories(@PathVariable("bucketName") String bucketName) {
    return ResponseEntity.ok(filesService.getRootDirectories(bucketName));
  }
}