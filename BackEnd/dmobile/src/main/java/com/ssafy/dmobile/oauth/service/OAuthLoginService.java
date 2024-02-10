package com.ssafy.dmobile.oauth.service;

import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.oauth.service.params.OAuthLoginParams;
import com.ssafy.dmobile.oauth.service.response.OAuthInfoResponse;
import com.ssafy.dmobile.utils.AuthTokenDto;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    // params 기준으로 login 실행

    public AuthTokenDto login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    //
    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .profileUrl(oAuthInfoResponse.getProfileImg())
                .oAuthType(oAuthInfoResponse.getOAuthInfo())
                .build();
        return memberRepository.save(member).getId();
    }
}
