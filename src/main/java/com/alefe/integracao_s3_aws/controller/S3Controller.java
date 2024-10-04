package com.alefe.integracao_s3_aws.controller;

import com.alefe.integracao_s3_aws.dto.PutObjectDTO;
import com.alefe.integracao_s3_aws.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service service;

    @PutMapping
    ResponseEntity<Void> putObject(
            @RequestBody PutObjectDTO putObjectDTO
    ) {
        service.putObject(putObjectDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping
    ResponseEntity<String> getObject(
            @RequestParam String bucketName,
            @RequestParam String fileName
    ) {
        return ResponseEntity.ok(service.getObjetct(fileName, bucketName));
    }

    @GetMapping("/get-objects")
    ResponseEntity<List<String>> getObjects(
            @RequestParam String bucketName
    ) {
        return ResponseEntity.ok(service.getObjects(bucketName));
    }

    @DeleteMapping
    ResponseEntity<Void> deleteObject(
            @RequestParam String bucketName,
            @RequestParam String fileName
    ) {
        service.deleteObject(bucketName, fileName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}

