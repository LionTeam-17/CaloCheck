package calocheck.base.util.s3.service;

import calocheck.base.util.s3.config.S3Config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Config s3Config;

    public InputStream getFileFromS3(String objectKey) throws IOException {
        S3Client s3Client = S3Client.builder()
                .region(Region.of(s3Config.getRegion()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(s3Config.getBucketName())
                    .key(objectKey)
                    .build();

            ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObject(getObjectRequest,
                    ResponseTransformer.toBytes());

            return responseBytes.asInputStream();
        } catch (S3Exception e) {
            throw new IOException("S3 파일 읽기 에러");
        }
    }
}
