package com.adarsh.processing.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;
import java.time.Instant;

@Entity
@Table(name = "processing_job")
public class ProcessingJob {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status;


    @Column(name = "processed_records", nullable = false)
    private Long processedRecords;

    @Column(name = "failed_records", nullable = false)
    private Long failedRecords;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Instant updatedAt;

    protected ProcessingJob() {
    }

    public UUID getId() {
        return id;
    }
    
    public String getFilePath() {
        return filePath;
    }

    public JobStatus getStatus() {
        return status;
    }

    public Long getProcessedRecords() {
        return processedRecords;
    }

    public Long getFailedRecords() {
        return failedRecords;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}   
