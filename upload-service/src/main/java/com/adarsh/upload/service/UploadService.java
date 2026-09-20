package com.adarsh.upload.service;

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.UUID;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adarsh.upload.model.ProcessingJob;
import com.adarsh.upload.repository.ProcessingJobRepository;

@Service 
public class UploadService {
    private final ProcessingJobRepository jobRepository;
    private final Path uploadDirectory;

    public UploadService(ProcessingJobRepository jobRepository, @Value("${streamforge.storage.upload-dir}") String uploadDirectory){
        this.jobRepository = jobRepository;
        this.uploadDirectory = Path.of(uploadDirectory).toAbsolutePath().normalize();
    }

    public ProcessingJob upload(MultipartFile file) {
        if (file == null || file.isEmpty()){ // File.isEmpty returns true for a zero byte file, this is intentional functionality however.
            throw new IllegalArgumentException("Uploaded file cannot be null or empty.");
        }

        String storedFilename = UUID.randomUUID().toString();
        Path destination = uploadDirectory.resolve(storedFilename);

        try {
            Files.createDirectories(uploadDirectory);
            try (InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destination);
            }

        } catch(IOException exception){
            throw new IllegalStateException("Filed to store upload file",exception);
        }

        ProcessingJob job = new ProcessingJob(destination.toString());
        return job;

    }
}
