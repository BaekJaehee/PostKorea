package com.ssafy.dmobile.oauth.service.response;

import com.ssafy.dmobile.oauth.service.OAuthType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

// Response 정보
// https://developers.naver.com/docs/login/profile/profile.md
public class NaverInfoResponse implements OAuthInfoResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {

//        private String name;
        private String email;
        private String nickname;
        private String profile_image;
//        private String gender;
//        private String birthyear;
    }

    @Override
    public String getEmail() {
        return response.email;
    }

    @Override
    public String getNickname() {
        return response.nickname;
    }

    @Override
    public String getProfileImg() {
        return response.profile_image;
    }

    @Override
    public OAuthType getOAuthInfo() {
        return OAuthType.NAVER;
    }
}
