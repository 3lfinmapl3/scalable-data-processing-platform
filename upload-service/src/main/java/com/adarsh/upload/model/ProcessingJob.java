package com.adarsh.upload.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    

    @Column(name = "file_path", nullable = false, columnDefinition = "TEXT")
    private String filePath;

    @JdbcTypeCode(SqlTypes.VARCHAR)
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

    protected ProcessingJob(){}

    public ProcessingJob(final String filePath){
        
        if (filePath == null || filePath.isEmpty()){
            throw new IllegalArgumentException("File Path cannot be null or empty !");
        }

        this.filePath = filePath;
        this.status = JobStatus.CREATED;
    }

    public UUID getId() {
        return this.id;
    }

    public String getFilePath(){
        return this.filePath;
    }

    public JobStatus getStatus(){
        return this.status;
    }

    public Long getProcessedRecords(){
        return this.processedRecords;
    }

    public Long getFailedRecords(){
        return this.failedRecords;
    }

    public Long getLastProcessedOffset(){
        return this.lastProcessedOffset;
    }

    public Instant getCreatedAt(){
        return this.createdAt;
    }

    public Instant getUpdatedAt(){
        return this.updatedAt;
    }

    public void startProcessing(){
        if (this.status != JobStatus.CREATED){
            throw new IllegalStateException("Job can't be processed in this state, current state : "+this.status);
        }

        this.status = JobStatus.IN_PROGRESS;
    }

    public void markCompleted(){
        if (this.status != JobStatus.IN_PROGRESS) {
            throw new IllegalStateException("Job can't be marked complete in this state, current state : "+this.status);
        }

        this.status = JobStatus.COMPLETED;
    }

    public void markFailed(){
        if (this.status != JobStatus.IN_PROGRESS) {
            throw new IllegalStateException("Job can't be marked failed in this state, current state : "+this.status);
        }

        this.status = JobStatus.FAILED;
    }
}
