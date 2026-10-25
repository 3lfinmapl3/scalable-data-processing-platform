package com.adarsh.upload.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.adarsh.upload.service.UploadService;
import com.adarsh.upload.model.ProcessingJob;
import com.adarsh.upload.dto.UploadResponse;

@RestController
@RequestMapping("/api/v1")
public class UploadController {
    private final UploadService uploadService;

    public UploadController(UploadService uploadService){
        this.uploadService = uploadService;
    }




    @PostMapping("/uploads")
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam MultipartFile file){

        ProcessingJob job = uploadService.upload(file);

        UploadResponse response = new UploadResponse(job.getId(), job.getStatus(), "File Uploaded Successfully");

        return ResponseEntity.accepted().body(response);
    } 
}
