package springboot.gcloud;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GoogleCloudStorage {

    final String bucketName = "nmt-spring-boot";

    private Storage storage() {
        return StorageOptions.getDefaultInstance().getService();
    }

    public String uploadAndGetUrl(MultipartFile file) {
        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().replace(" ", "_");
        BlobId blobId = BlobId.of(bucketName, uniqueFileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        try {
            storage().create(blobInfo, file.getBytes());
            return "https://storage.googleapis.com/nmt-spring-boot/" + uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
