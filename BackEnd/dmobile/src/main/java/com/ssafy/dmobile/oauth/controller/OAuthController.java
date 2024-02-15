package com.ssafy.dmobile.oauth.controller;

import com.ssafy.dmobile.oauth.service.OAuthLoginService;
import com.ssafy.dmobile.oauth.service.params.KakaoLoginParams;
import com.ssafy.dmobile.oauth.service.params.NaverLoginParams;
import com.ssafy.dmobile.common.utils.AuthTokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Tag(name = "OAuth", description = "소셜 로그인 API Document")
public class OAuthController {

    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/kakao")
    @Operation(summary = "카카오 로그인", description = "현재 사용되지 않습니다.")
    public ResponseEntity<AuthTokenDto> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @PostMapping("/naver")
    @Operation(summary = "네이버 로그인", description = "현재 사용되지 않습니다.")
    public ResponseEntity<AuthTokenDto> loginNaver(@RequestBody NaverLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }
}
