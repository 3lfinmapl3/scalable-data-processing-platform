package com.adarsh.upload.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class UploadController {
    @PostMapping("/uploads")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file){
        return ResponseEntity.accepted().body("Upload Received !");
    } 
}
