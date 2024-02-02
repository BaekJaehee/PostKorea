package com.ssafy.dmobile.member.controller;

import com.ssafy.dmobile.member.service.MemberService;
import com.ssafy.dmobile.member.service.ProfileService;
import com.ssafy.dmobile.utils.AuthTokensGenerator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/profile")
@Tag(name = "Profile", description = "회원 프로필 API Document")
public class ProfileController {

    private final MemberService memberService;
    private final ProfileService profileService;
    private final AuthTokensGenerator authTokensGenerator;

    // 사용자
    @PostMapping(value = "/image", consumes = "multipart/form-data")
    @Operation(summary = "프로필 이미지 업로드", description = "사용자의 프로필 이미지를 업로드합니다. 인가 과정에서 Token이 사용됩니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> uploadProfileImage(
            @RequestHeader("Authorization") String token,
            @RequestPart MultipartFile file) throws IOException {
            Long memberId = authTokensGenerator.extractMemberId(token);
            String fileName = profileService.uploadProfileImage(memberId, file);

            Map<String, Object> response = new HashMap<>();
            response.put("code", 0);
            response.put("msg", "succeed");
            response.put("data", fileName);

            return ResponseEntity.ok(response); // 200
    }
    
    // 이미지 조회 시, 친구 등 다른 사용자의 프로필 이미지도 조회할 수 있어야함
    // 지금은 토큰을 통해 조회가능하며 차후 토큰 -> memberId -> 친구목록 조회 기능도 추가 예정
    @GetMapping(value = "/image", produces = "multipart/form-data")
    @Operation(summary = "프로필 이미지 조회", description = "사용자의 프로필 이미지를 조회합니다. 인가 과정에서 Token이 사용됩니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> downloadProfileImage(
            @RequestHeader("Authorization") String token) throws MalformedURLException {
        Long memberId = authTokensGenerator.extractMemberId(token);
        Resource resource = profileService.downloadImage(memberId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
