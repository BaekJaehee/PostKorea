package com.ssafy.dmobile.oauth.service.client;

import com.ssafy.dmobile.oauth.service.OAuthType;
import com.ssafy.dmobile.oauth.service.params.OAuthLoginParams;
import com.ssafy.dmobile.oauth.service.response.OAuthInfoResponse;

public interface OAuthApiClient {
    OAuthType oAuthInfo();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOAuthInfo(String accessToken);
}
