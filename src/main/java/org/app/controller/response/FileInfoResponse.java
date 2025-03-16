package org.app.controller.response;

public record FileInfoResponse(long fileId, String fileName, String fileOwnerId,
                               String fileCreationDate, String fileLastModifiedDate,
                               String fileAccessLevel, String fileTags) {}
