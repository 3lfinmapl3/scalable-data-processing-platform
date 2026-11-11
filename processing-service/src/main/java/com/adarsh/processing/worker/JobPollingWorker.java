package com.adarsh.processing.worker;

import com.adarsh.processing.service.JobClaimService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobPollingWorker {

    private final JobClaimService jobClaimService;

    public JobPollingWorker(JobClaimService jobClaimService) {
        this.jobClaimService = jobClaimService;
    }

    @Scheduled(fixedDelay = 5000)
    public void pollForJobs() {

        jobClaimService.claimNextJob()
                .ifPresent(jobId ->
                        System.out.println("Claimed job: " + jobId)
                );
    }
}