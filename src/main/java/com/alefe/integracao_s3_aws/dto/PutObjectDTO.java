package com.alefe.integracao_s3_aws.dto;

public record PutObjectDTO(
        String base64AsAFile,
        String fileName,
        String bucketName
) { }
