package com.ssafy.dmobile.oauth.service.client;

import com.ssafy.dmobile.oauth.service.OAuthType;
import com.ssafy.dmobile.oauth.service.params.OAuthLoginParams;
import com.ssafy.dmobile.oauth.service.response.NaverInfoResponse;
import com.ssafy.dmobile.oauth.service.response.OAuthInfoResponse;
import com.ssafy.dmobile.oauth.service.token.NaverTokens;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NaverApiClient implements OAuthApiClient {

    private static final Logger logger = LoggerFactory.getLogger(NaverApiClient.class);

    // url 및 client_id는 application.yml 정의
    public static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.naver.url.auth}")
    private String authUrl;

    @Value("${oauth.naver.url.api}")
    private String apiUrl;

    @Value("${oauth.naver.client-id}")
    private String clientId;

    private final RestTemplate restTemplate;

    @Override
    public OAuthType oAuthInfo() {
        return OAuthType.NAVER;
    }

    // 네이버 로그인 API 명세
    // https://developers.naver.com/docs/login/api/api.md
    @Override
    public String requestAccessToken(OAuthLoginParams params) {

        // json 출력, URL 리다이렉트 시 token 대신 authorize 작성
        String url = authUrl + "/oauth2.0/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        // 발급:'authorization_code', 갱신:'refresh_token', 삭제: 'delete'
        body.add("grant_type", "authorization_code");
        // code, state 는 params 에서 미리 정의해둠
        body.add("client_id", clientId);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        NaverTokens response = restTemplate.postForObject(url, request, NaverTokens.class);

        // 미리 정의해둔 NaverTokens 객체로 응답 결과 저장
        assert response != null;
        return response.getAccessToken();
    }

    // 네이버 회원 프로필 조회 API 명세
    // https://developers.naver.com/docs/login/profile/profile.md
    @Override
    public OAuthInfoResponse requestOAuthInfo(String accessToken) {

        String url = apiUrl + "/v1/nid/me";
        String token = "Bearer " + accessToken;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", token);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        // 미리 정의해둔 NaverInfoResponse 객체로 응답 결과 저장
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, NaverInfoResponse.class);
    }
}
