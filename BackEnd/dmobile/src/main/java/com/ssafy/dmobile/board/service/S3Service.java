package com.ssafy.dmobile.board.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    //
    @PostConstruct // PostConstruct : 스프링 컨테이너가 이 서비스 인스턴스를 생성한 후 초기화 과정에서 자동으로 실행된다.
    private void initializeAmazon() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    // MultipartFile을 S3 버킷에 업로드하는 메서드
    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            // generateFileName을 사용해 업로드할 파일의 이름을 생성한다.
            String fileName = generateFileName(multipartFile);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            metadata.setContentLength(multipartFile.getSize());

            s3Client.putObject(new PutObjectRequest(bucketName, fileName, multipartFile.getInputStream(), metadata));
            // uploadFileTos3bucket를 호출하여 실제로 S3 버킷에 파일을 업로드한다.
            fileUrl = s3Client.getUrl(bucketName, fileName).toString();
//            uploadFileTos3bucket(fileName, file);
            // 임시로 생성된 File 객체를 삭제한다.
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    // MultipartFile을 File 객체로 변환한다.
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    // 업로드할 파일의 이름을 결정한다, 현제는 원본 파일 이름을 그대로 사용한다.
    private String generateFileName(MultipartFile multiPart) {
        // 원본 파일 이름에서 확장자 추출
        String originalFileName = multiPart.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // UUID를 이용하여 유니크한 파일명 생성
        return UUID.randomUUID().toString() + extension;
    }

    // putObject 객체를 생성해, 지정된 버켓에 파일을 업로드 한다.
    private void uploadFileTos3bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
    }

    // 다운로드 메서드는 필요에 따라 구현
}