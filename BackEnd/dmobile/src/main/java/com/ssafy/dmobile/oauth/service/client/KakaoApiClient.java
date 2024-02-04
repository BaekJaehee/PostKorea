package com.ssafy.dmobile.oauth.service.client;

import com.ssafy.dmobile.oauth.service.OAuthType;
import com.ssafy.dmobile.oauth.service.params.OAuthLoginParams;
import com.ssafy.dmobile.oauth.service.response.KakaoInfoResponse;
import com.ssafy.dmobile.oauth.service.response.OAuthInfoResponse;
import com.ssafy.dmobile.oauth.service.token.KakaoTokens;
import lombok.RequiredArgsConstructor;
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
public class KakaoApiClient implements OAuthApiClient {

    // url 및 client_id는 application.yml 정의
    public static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;

    @Value("${oauth.kakao.url.api}")
    private String apiUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    private final RestTemplate restTemplate;

    @Override
    public OAuthType oAuthInfo() {
        return OAuthType.KAKAO;
    }

    // 토큰 갱신하기
    // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#refresh-token
    @Override
    public String requestAccessToken(OAuthLoginParams params) {

        String url = authUrl + "/oauth/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE); // authorization_code로 고정
        body.add("client_id", clientId);    // 앱 REST API 키

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        KakaoTokens response = restTemplate.postForObject(url, request, KakaoTokens.class);
        // 미리 정의해둔 KakaoTokens 객체로 응답 결과 저장
        assert response != null;
        return response.getAccessToken();
    }

    // 사용자 정보 가져오기
    // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
    @Override
    public OAuthInfoResponse requestOAuthInfo(String accessToken) {

        String url = apiUrl + "/v2/user/me";
        String token = "Header" + accessToken;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", token);   // 인증 방식, 액세스 토큰으로 인증 요청

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        // Property 키 목록, JSON Array 사용
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile]\"");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        // 미리 정의해둔 KakaoInfoResponse 객체로 응답 결과 저장
        return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
    }
}
