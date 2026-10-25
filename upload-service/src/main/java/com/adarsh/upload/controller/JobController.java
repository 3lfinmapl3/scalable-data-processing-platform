package com.adarsh.upload.controller;

import org.springframework.web.bind.annotation.*;
import com.adarsh.upload.service.JobService;
import com.adarsh.upload.dto.JobStatusResponse;
import org.springframework.http.ResponseEntity;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobStatusResponse> getJobStatus(
        @PathVariable UUID jobId
    )
    {
        return jobService.getJobStatus(jobId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
