package calocheck.base.util.s3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Value("${excel.aws.region}")
    private String region;
    @Value("${excel.aws.bucket}")
    private String bucketName;

    public String getRegion() { return region; }
    public String getBucketName() {return bucketName; }
}
