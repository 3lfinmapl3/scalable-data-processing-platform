package com.adarsh.upload.dto;

import com.adarsh.upload.model.JobStatus;
import java.util.UUID;
import java.time.Instant;



public record JobStatusResponse(
    UUID id,
    JobStatus status,
    Long processedRecords,
    Long failedRecords,
    Instant createdAt,
    Instant updatedAt
){}
