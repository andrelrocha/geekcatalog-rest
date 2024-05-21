package rocha.andre.api.infra.utils.aws;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class DependencyFactory {

    @Getter
    @Value("${amazon.s3.bucket-name}")
    private String bucketName;

    @Getter
    @Value("${amazon.s3.endpoint}")
    private String endpoint;

    @Value("${amazon.s3.access-key}")
    private String accessKey;

    @Value("${amazon.s3.secret-key}")
    private String secretKey;


    public S3Client s3Client() {
        var credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .region(Region.SA_EAST_1)
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(() -> credentials)
                .build();
    }
}