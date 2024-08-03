package springboot.aws;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3Service {
    private final String accessKey = "AKIAQFLZDQQQEYA25275";
    private final String secretKey = "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";
    private final Region region = Region.AP_EAST_1;

    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey))
                .region(region)
                .build();

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public String uploadAndGetUrl(MultipartFile file) {
        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().replace(" ", "_");
        try (S3Client s3 = this.s3Client()) {
            try {
                File uploadedFile = convertMultiPartToFile(file);
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket("app.tn.images")
                        .key(uniqueFileName)
                        .build();
                s3.putObject(putObjectRequest, uploadedFile.toPath());
                return "https://s3.ap-east-1.amazonaws.com/app.tn.images/" + uniqueFileName;
            } catch (IOException e) {
                return null;
            }
        }
    }
}
