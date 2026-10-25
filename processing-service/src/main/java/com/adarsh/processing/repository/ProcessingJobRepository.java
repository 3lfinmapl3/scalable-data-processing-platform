package com.adarsh.processing.repository;

import com.adarsh.processing.model.ProcessingJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ProcessingJobRepository
        extends JpaRepository<ProcessingJob, UUID> {

    @Modifying
    @Query(value = """
        WITH next_job AS (
            SELECT id
            FROM processing_job
            WHERE status = 'CREATED'
            ORDER BY created_at, id
            FOR UPDATE SKIP LOCKED
            LIMIT 1
        )
        UPDATE processing_job
        SET status = 'IN_PROGRESS'
        WHERE id IN (SELECT id FROM next_job)
        RETURNING id
        """, nativeQuery = true)
    UUID claimNextJob();
}