package com.ssafy.dmobile.oauth.service.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Response 관련 정보
// https://developers.naver.com/docs/login/api/api.md#4-2--%EC%A0%91%EA%B7%BC-%ED%86%A0%ED%81%B0-%EB%B0%9C%EA%B8%89-%EC%9A%94%EC%B2%AD
@Getter
@NoArgsConstructor
@ToString
public class NaverTokens {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private String expiresIn;
}
