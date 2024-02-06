package com.ssafy.dmobile.member.service;

import com.ssafy.dmobile.Board.service.S3Service;
import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class ProfileService {

//    @Value("${file.upload-dir}")
//    private String uploadDir;

    @Value("${file.max-file-size}")
    private String maxFileSize;

    private final MemberRepository memberRepository;
    private final S3Service s3Service;

//    @Transactional
//    public String uploadProfileImage(Long id, MultipartFile file) throws IOException {
//
//        // 잘못된 토큰을 입력받은 경우
//        if (!memberRepository.existsById(id)) {
//            throw new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION);
//        }
//
//        // 파일의 용량이 5MB를 초과하는 경우
//        long parsedMaxFileSize = DataSize.parse(maxFileSize).toBytes();
//        if (file.getSize() > parsedMaxFileSize) {
//            throw new CustomException(ExceptionType.MAX_FILE_SIZE_EXCEPTION);
//        }
//
//        // 저장 시 중복 방지를 위해 사용자 아이디로 파일명 수정
//        String fileName = id + ".PNG";
//        Path filePath = Paths.get(uploadDir, fileName);
//
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        // memberId 값으로 저장하니까 굳이 profile_img 컬럼이 필요가 없음
////        memberRepository.updateProfileImage(memberId, filePath.toString());
//        return fileName;
//    }

    @Transactional
    public String uploadProfileImage(Long id, MultipartFile file) throws IOException {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));

        long parsedMaxFileSize = DataSize.parse(maxFileSize).toBytes();
        if (file.getSize() > parsedMaxFileSize) {
            throw new CustomException(ExceptionType.MAX_FILE_SIZE_EXCEPTION);
        }

        String profileUrl = s3Service.uploadFile(file);
        member.setProfileUrl(profileUrl);
        memberRepository.save(member);
        return profileUrl;
    }


//    public Resource downloadImage(Long id) throws MalformedURLException {
//
//        // 잘못된 토큰을 입력받은 경우
//        if (!memberRepository.existsById(id)) {
//            throw new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION);
//        }
//
//        String fileName = id + ".PNG";
//        Path filePath = Paths.get(uploadDir, fileName).normalize();
//        Resource resource = new UrlResource(filePath.toUri());
//
//        if (!resource.exists()) {
//            throw new CustomException(ExceptionType.FILE_NOT_FOUND_EXCEPTION);
//        }
//
//        return resource;
//    }
}
