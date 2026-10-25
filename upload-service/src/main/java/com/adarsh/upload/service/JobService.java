package com.adarsh.upload.service;

import com.adarsh.upload.model.ProcessingJob;
import com.adarsh.upload.repository.ProcessingJobRepository;
import com.adarsh.upload.dto.JobStatusResponse;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.Optional;

@Service
public class JobService {
    private final ProcessingJobRepository jobRepository;

    public JobService(ProcessingJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    public Optional<JobStatusResponse> getJobStatus(UUID jobId){
        return jobRepository.findById(jobId)
        .map(job -> new JobStatusResponse(job.getId(), 
        job.getStatus(),
        job.getProcessedRecords(),
        job.getFailedRecords(),
        job.getCreatedAt(),
        job.getUpdatedAt()
    ));
    }

}
