package com.adarsh.upload.dto;

import java.util.UUID;
import com.adarsh.upload.model.JobStatus;

public record UploadResponse(
    UUID id,
    JobStatus status,
    String message
){}