//package com.hab.blog.api.v1.controller;
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.net.URL;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
//@RestController
//@RequestMapping("/api/upload")
//public class FileUploadController {
//
//    @Value("${aliyun.oss.endpoint}")
//    private String endpoint;
//
//    @Value("${aliyun.oss.accessKeyId}")
//    private String accessKeyId;
//
//    @Value("${aliyun.oss.accessKeySecret}")
//    private String accessKeySecret;
//
//    @Value("${aliyun.oss.bucketName}")
//    private String bucketName;
//
//
//    @PostMapping
//    public String uploadFile(@RequestParam("file") MultipartFile file,
//                             @RequestParam("file_type") String fileType,
//                             @RequestParam("user_id") String userId) throws Exception {
//        // 验证文件类型等逻辑可以在这里添加
//
//        // 加密用户ID
//        String encryptedUserId = encryptUserId(userId); // 假设encryptUserId是你的自定义加密方法
//
//        // 创建OSS客户端实例
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//        // 文件上传到OSS
//        String objectName = encryptedUserId + "/" + file.getOriginalFilename(); // 创建文件路径
//        ossClient.putObject(bucketName, objectName, file.getInputStream());
//        file.getInputStream().close();
//
//        Date expirationDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
//
//        // 获取文件的URL
//        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expirationDate);
//
//        // 关闭OSS客户端
//        ossClient.shutdown();
//
//        // 保存URL到Redis
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        String key = "files:" + encryptedUserId + ":" + fileType; // 生成key
//        ops.set(key, url.toString(), 30, TimeUnit.DAYS); // 设置30天过期
//
//        // 返回key给客户端
//        return key;
//    }
//
//    private String encryptUserId(String userId) {
//        // 这里应该是加密逻辑
//        return userId; // 这里是一个占位符，实际上你应该替换成加密方法
//    }
//}
