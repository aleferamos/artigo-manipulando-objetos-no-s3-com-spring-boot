package com.alefe.integracao_s3_aws.service;

import com.alefe.integracao_s3_aws.dto.PutObjectDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    public String putObject(PutObjectDTO putObjectDTO) {
        byte[] decodedBytes = Base64.getDecoder().decode(putObjectDTO.base64AsAFile());

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(putObjectDTO.bucketName())
                .key(putObjectDTO.fileName())
                .build();

        PutObjectResponse putResponse = s3Client.putObject(putRequest, RequestBody.fromBytes(decodedBytes));

        return putResponse.eTag();
    }

    public String getObjetct(String fileName, String bucketName) {
        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        try {
            return Base64.getEncoder().encodeToString(s3Client.getObject(getRequest).readAllBytes());
        } catch (Exception e) {
            throw new InternalException(e);
        }
    }

    public List<String> getObjects(String bucketName) {
        ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);

        return listResponse.contents().stream().map(S3Object::key).toList();
    }

    public void deleteObject(String bucketName, String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}
