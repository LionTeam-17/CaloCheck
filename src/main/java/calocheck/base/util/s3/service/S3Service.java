package calocheck.base.util.s3.service;

import calocheck.base.util.s3.config.S3Config;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
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
    private final AmazonS3 s3Client;

    public InputStream getFileFromS3(String objectKey) throws IOException {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(s3Config.getBucket())
                    .key(objectKey)
                    .build();

            S3Object s3Object = s3Client.getObject(s3Config.getBucket(), objectKey);

            return s3Object.getObjectContent();
        } catch (S3Exception e) {
            throw new IOException("S3 파일 읽기 에러");
        }
    }
}
