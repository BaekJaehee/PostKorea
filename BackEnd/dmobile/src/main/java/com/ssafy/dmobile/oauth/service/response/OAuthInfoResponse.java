package com.ssafy.dmobile.oauth.service.response;

import com.ssafy.dmobile.oauth.service.OAuthType;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    String getProfileImg();
    OAuthType getOAuthInfo();
}
