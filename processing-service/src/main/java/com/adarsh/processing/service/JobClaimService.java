package com.adarsh.processing.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JobClaimService {

    private final JdbcTemplate jdbcTemplate;

    public JobClaimService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UUID> claimNextJob() {

        String sql = """
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
            """;

        return jdbcTemplate.query(
                sql,
                rs -> rs.next()
                        ? Optional.of(rs.getObject("id", UUID.class))
                        : Optional.empty()
        );
    }
}