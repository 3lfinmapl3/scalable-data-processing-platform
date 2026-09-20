package com.adarsh.upload.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adarsh.upload.model.ProcessingJob;

public interface ProcessingJobRepository extends JpaRepository<ProcessingJob, UUID>{

}
