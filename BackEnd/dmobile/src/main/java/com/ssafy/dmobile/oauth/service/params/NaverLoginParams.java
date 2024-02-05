package com.ssafy.dmobile.oauth.service.params;

import com.ssafy.dmobile.oauth.service.OAuthType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
public class NaverLoginParams implements OAuthLoginParams {

    private String authorizationCode;
    private String state;

    @Override
    public OAuthType oAuthInfo() {
        return OAuthType.NAVER;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("state", state);

        return body;
    }
}
