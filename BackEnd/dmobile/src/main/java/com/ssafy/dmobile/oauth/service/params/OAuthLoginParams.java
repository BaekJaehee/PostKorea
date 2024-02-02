package com.ssafy.dmobile.oauth.service.params;

import com.ssafy.dmobile.oauth.service.OAuthType;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthType oAuthInfo();
    MultiValueMap<String, String> makeBody();
}
