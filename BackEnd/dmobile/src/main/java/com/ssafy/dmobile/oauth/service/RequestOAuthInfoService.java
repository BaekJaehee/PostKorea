package com.ssafy.dmobile.oauth.service;

import com.ssafy.dmobile.oauth.service.client.OAuthApiClient;
import com.ssafy.dmobile.oauth.service.params.OAuthLoginParams;
import com.ssafy.dmobile.oauth.service.response.OAuthInfoResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {

    private final Map<OAuthType, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthInfo, Function.identity())
        );
    }

    public OAuthInfoResponse request(OAuthLoginParams params) {
        // API 제공자 식별
        OAuthApiClient client = clients.get(params.oAuthInfo());
        String accessToken = client.requestAccessToken(params);
        return client.requestOAuthInfo(accessToken);
    }
}
