package com.ssafy.dmobile.oauth.service.response;

import com.ssafy.dmobile.oauth.service.OAuthType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

// Response 정보
// https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private KakaoProfile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {
        private String nickname;
        private String profile_image_url;
    }

    @Override
    public String getEmail() {
        return kakaoAccount.email;
    }

    @Override
    public String getNickname() {
        return kakaoAccount.profile.nickname;
    }

    @Override
    public String getProfileImg() {
        return kakaoAccount.profile.profile_image_url;
    }

    @Override
    public OAuthType getOAuthInfo() {
        return OAuthType.KAKAO;
    }
}
