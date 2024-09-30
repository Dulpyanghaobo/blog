package com.hab.blog.feature.v1.utility;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Date;

@Slf4j
@Component
public class OssUtility {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    private volatile OSS ossClient;

    private OSS getOssClient() {
        if (ossClient == null) {
            synchronized (this) {
                if (ossClient == null) {
                    ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
                }
            }
        }
        return ossClient;
    }

    public String uploadFile(MultipartFile file, String dictionary) throws Exception {
        if (file.isEmpty() || dictionary == null || dictionary.trim().isEmpty()) {
            throw new IllegalArgumentException("File or directory cannot be null or empty");
        }

        OSS client = getOssClient();
        try {
            String objectName = dictionary + "/" + file.getOriginalFilename();
            // Upload the file to OSS
            client.putObject(bucketName, objectName, file.getInputStream());

            // Generate the URL for the uploaded file
            // Set URL expiration time if necessary
            Date expiration = new Date(new Date().getTime() + 100 * 365 * 24 * 60 * 1000L);
            URL url = client.generatePresignedUrl(bucketName, objectName, expiration);

            return url.toString();
        } catch (Exception e) {
            log.error("Error uploading file to OSS", e);
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    @PreDestroy
    public void destroy() {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }
}
