package calocheck.base.util.s3.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Value("${excel.aws.accessKey}")
    private String accessKey;
    @Value("${excel.aws.secretKey}")
    private String secretKey;
    @Value("${excel.aws.region}")
    private String region;
    @Value("${excel.aws.bucket}")
    private String bucketName;

    public String getRegion() { return region; }
    public String getBucketName() {return bucketName; }

    @Bean
    public AmazonS3Client amazonS3Client2() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region).enablePathStyleAccess()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
