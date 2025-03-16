package org.app.repository;

import org.app.controller.response.FileInfoResponse;
import org.app.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<File, Long> {
  FileInfoResponse findFileByFileId(long fileId);
}