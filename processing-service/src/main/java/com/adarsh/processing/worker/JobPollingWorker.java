package com.adarsh.processing.worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adarsh.processing.service.JobClaimService;
import com.adarsh.processing.service.JobProcessingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobPollingWorker {

    private static final Logger logger = LoggerFactory.getLogger(JobPollingWorker.class);

    private final JobClaimService jobClaimService;
    private final JobProcessingService jobProcessingService;
    
    public JobPollingWorker(JobClaimService jobClaimService, JobProcessingService jobProcessingService) {
        this.jobClaimService = jobClaimService;
        this.jobProcessingService = jobProcessingService;
    }

    @Scheduled(fixedDelay = 5000)
    public void pollForJobs() {

        jobClaimService.claimNextJob()
                .ifPresent(jobId ->{

                        logger.info("Claimed job {}", jobId);

                        jobProcessingService.process(jobId);
        });
    }
}