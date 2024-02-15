package com.ssafy.dmobile.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenDto {

    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static AuthTokenDto of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new AuthTokenDto(accessToken, refreshToken, grantType, expiresIn);
    }
}
