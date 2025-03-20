package com.example.minio_storage.integration;

import com.example.minio_storage.common.utils.ConverterUtils;
import com.example.minio_storage.exception.AppException;
import com.example.minio_storage.exception.ErrorCode;
import com.google.common.net.HttpHeaders;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
//Là 1 bean để giao tiếp với minioChanel
public class MinioChannel {
    private static final String BUCKET = "resources";
    private final MinioClient minioClient;

    //Để mặc định là sẽ tạo bucket (thư mục) để lưu file
    //PostConstruct là sẽ gọi init() ngay sau khi bean được khởi tạo
    @PostConstruct
    private void init() {
        createBucket(BUCKET);
    }

    //
    //Annotation của Lombok cho phép ném các ngoại lệ kiểm tra mà không cần khai báo throws Exception trong chữ ký phương thức
    @SneakyThrows
    private void createBucket(final String name) {
        // Kiểm tra nếu bucket đã tồn tại
        final var found = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(name)
                        .build()
        );
        if (!found) {
            // Tạo bucket nếu chưa tồn tại
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(name)
                            .build()
            );

            //policy bình thường là private , đây là 1 package private nên không thể truy cập trực tiếp mà phải thông qua backend
            //tạo public
            // Thiết lập bucket là public bằng cách set policy
            final var policy = """
                        {
                          "Version": "2012-10-17",
                          "Statement": [
                           {
                              "Effect": "Allow",
                              "Principal": "*",
                              "Action": "s3:GetObject",
                              "Resource": "arn:aws:s3:::%s/*"
                            }
                          ]
                        }
                    """.formatted(name);
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder().bucket(name).config(policy).build()
            );
        } else {
            log.info("Bucket %s đã tồn tại.".formatted(name));
        }
    }
    @SneakyThrows
    public String upload(@NonNull final MultipartFile file, String folderPath) {
        //sau này muốn lưu kiểu này phân chia theo loại
//        log.info("Bucket: {}, file size: {}", BUCKET,"/user/avatar", file.getSize());
        log.info("Bucket: {}, file size: {}", BUCKET, file.getSize());
//        final var fileName = file.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        final var fileName = folderPath +UUID.randomUUID().toString() +"."+ fileExtension;
        try {
            file.getContentType();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET)
                            .object(fileName)
                            .contentType(file.getContentType())
                            //getInputStream để đẩy lên server
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );
        } catch (Exception ex) {
            log.error("Error saving image \n {} ", ex.getMessage());
            throw new AppException(ErrorCode.UNABLE_UPLOAD_FILE);
        }
        return minioClient.getPresignedObjectUrl(
                io.minio.GetPresignedObjectUrlArgs.builder()
                        .method(io.minio.http.Method.GET)
                        .bucket(BUCKET)
                        .object(fileName)
                        .build()
        );
    }

    public byte[] download(String bucket, String name) {
        try (GetObjectResponse inputStream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(name)
                .build())) {
            String contentLength = inputStream.headers().get(HttpHeaders.CONTENT_LENGTH);
            int size = StringUtils.isEmpty(contentLength) ? 0 : Integer.parseInt(contentLength);
            return ConverterUtils.readBytesFromInputStream(inputStream, size);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNABLE_DOWNLOAD_FILE);
        }
    }
}