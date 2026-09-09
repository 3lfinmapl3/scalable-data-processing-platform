package com.adarsh.upload.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;

@Entity
@Table(name = "processing_job")
public class ProcessingJob {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobStatus status;
    
    @Column(name = "processed_records", nullable = false)
    private Long processedRecords = 0L;
    
    @Column(name = "failed_records", nullable = false)
    private Long failedRecords = 0L;
    
    @Column(name = "last_processed_offset")
    private Long lastProcessedOffset;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
