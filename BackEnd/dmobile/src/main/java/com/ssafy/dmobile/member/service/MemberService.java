package com.ssafy.dmobile.member.service;

import com.ssafy.dmobile.achievements.entity.achieve.Achieve;
import com.ssafy.dmobile.achievements.repository.AchieveMemberRepository;
import com.ssafy.dmobile.achievements.repository.AchieveRepository;
import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.entity.request.*;
import com.ssafy.dmobile.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AchieveRepository achieveRepository;
    private final AchieveMemberRepository achieveMemberRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // 회원가입
    @Transactional
    public Member registerMember(MemberRequestDto memberRequestDTO) {
        
        // 이메일 중복 체크
        if(memberRepository.existsByEmail(memberRequestDTO.getMemberEmail())) {
            throw new CustomException(ExceptionType.DUPLICATE_EMAIL_EXCEPTION);
        }

        // 닉네임 중복 체크
        if (memberRepository.existsByNickname(memberRequestDTO.getMemberNickname())) {
            throw new CustomException(ExceptionType.DUPLICATE_NICKNAME_EXCEPTION);
        }

        // 비밀번호가 생성 규칙 체크 (예. 4자리 미만)
        if (memberRequestDTO.getMemberPwd().length() < 4) {
            throw new CustomException(ExceptionType.INVALID_PASSWORD_FORMAT_EXCEPTION);
        }
        
        // Spring Security 를 통한 비밀번호 암호화
        String pwd = passwordEncoder.encode(memberRequestDTO.getMemberPwd());

        // DTO 기반 Member 엔티티 생성
        Member newMember = Member.builder()
                .email(memberRequestDTO.getMemberEmail())
                .password(pwd)
                .name(memberRequestDTO.getMemberName())
                .nickname(memberRequestDTO.getMemberNickname())
                .age(memberRequestDTO.getMemberAge())
                .gender(memberRequestDTO.getMemberGender())
                // 프로필 이미지의 경우 추가적인 작업 필요
                .oAuthType(memberRequestDTO.getMemberAuth())
                .role(memberRequestDTO.getMemberRole())
                .point(0)
                .build();
        
        return memberRepository.save(newMember);
    }

    // 로그인
    public Member loginMember(MemberLoginRequestDto memberDto) {

        String email = memberDto.getMemberEmail();
        String password = memberDto.getMemberPwd();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        // 이메일 정보 없는 경우
        if (optionalMember.isEmpty()) {
            throw new CustomException(ExceptionType.INVALID_LOGIN_EXCEPTION);
        }

        Member member = optionalMember.get();
        // 비밀번호 일치하지 않는 경우
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ExceptionType.INVALID_LOGIN_EXCEPTION);
        }

        return member;
    }

    public Member getMemberDetails(Long id) {

        Optional<Member> optionalMember = memberRepository.findById(id);
        // 유저 정보가 없는 경우
        if (optionalMember.isEmpty()) {
            throw new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION);
        }

//        Map<String, Object> response = new HashMap<>();

        // 아이디, OAuth 정보 생략
//        response.put("member_email", member.getEmail());
//        response.put("member_name", member.getName());
//        response.put("member_nickname", member.getNickname());
//        response.put("member_age", member.getAge());
//        response.put("member_gender", member.getGender());
//        response.put("member_point", member.getPoint());

        return optionalMember.get();
    }

    @Transactional
    public void editMemberInfo(Long id, MemberEditRequestDto memberDto) {

        Optional<Member> optionalMember = memberRepository.findById(id);
        // 유저 정보가 없는 경우
        if (optionalMember.isEmpty()) {
            throw new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION);
        }

        Member modifiedMember = optionalMember.get();
        Member.MemberBuilder builder = modifiedMember.toBuilder();
        // 현재 변경 가능한 필드 : 닉네임, 나이, 성별 (변경가능)

        // 닉네임 중복 체크
        if (memberRepository.existsByNickname(memberDto.getMemberNickname())) {
            throw new CustomException(ExceptionType.DUPLICATE_NICKNAME_EXCEPTION);
        }

        if (memberDto.getMemberNickname() != null) {
            builder.nickname(memberDto.getMemberNickname());
        }
        if (memberDto.getMemberAge() != null) {
            builder.age(memberDto.getMemberAge());
        }

        memberRepository.save(builder.build());
    }

    @Transactional
    public void editMemberPassword(Long id, MemberEditPwdRequestDto pwdDto) {

        Optional<Member> optionalMember = memberRepository.findById(id);
        // 유저 정보가 없는 경우
        if (optionalMember.isEmpty()) {
            throw new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION);
        }

        Member modifiedMember = optionalMember.get();
        // 비밀번호 일치하지 않는 경우
        if (!passwordEncoder.matches(pwdDto.getCurrentPwd(), modifiedMember.getPassword())) {
            throw new CustomException(ExceptionType.INVALID_CURRENT_PASSWORD_EXCEPTION);
        }

        Member.MemberBuilder builder = modifiedMember.toBuilder();
        // 비밀번호가 생성 규칙 체크 (예. 4자리 미만)
        if (pwdDto.getNewPwd().length() < 4) {
            throw new CustomException(ExceptionType.INVALID_PASSWORD_FORMAT_EXCEPTION);
        }

        // Spring Security 를 통한 비밀번호 암호화
        String password = passwordEncoder.encode(pwdDto.getNewPwd());
        builder.password(password);

        memberRepository.save(builder.build());
    }

    @Transactional
    public void removeMember(Long id) {
        memberRepository.deleteById(id);
    }

    public String generateTempoPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    @Transactional
    public void editMemberTempoPassword(MemberFindPwdRequestDto pwdDto) {

        Member member = memberRepository.findByEmail(pwdDto.getMemberEmail())
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));

        // 임시 비밀번호 생성
        String tempoPassword = generateTempoPassword(8);
        Member.MemberBuilder builder = member.toBuilder();

        // Spring Security 를 통한 비밀번호 암호화
        String password = passwordEncoder.encode(tempoPassword);
        builder.password(password);

        // 비밀번호 업데이트
        Member savedMember = memberRepository.save(builder.build());

        // 이메일 보내기
        emailService.sendTemporaryPassword(savedMember.getEmail(), savedMember.getName(), tempoPassword);
    }

    @Transactional
    public void editMemberTitleAchieve(Long memberId, Long achieveId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));

        List<Achieve> deniedAchieves = achieveMemberRepository.findByAchievesNotOwnedByMember(memberId);
        for (Achieve achieve : deniedAchieves) {

            if(Objects.equals(achieve.getAchieveId(), achieveId)) {
                throw new CustomException(ExceptionType.INVALID_MEMBER_FOR_ACHIEVE_EXCEPTION);
            }
        }

        Member.MemberBuilder builder = member.toBuilder();
        builder.achieve(achieveRepository.getReferenceById(achieveId).getAchieveName());

        memberRepository.save(builder.build());
    }

    // Direct JPA Resources -----
    public Long getMemberIdByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(Member::getId)
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));
    }

    public String getMemberEmailById(Long id) {
        return memberRepository.findById(id)
                .map(Member::getEmail)
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));
    }
//    public boolean existsById(Long id) {
//        return memberRepository.existsById(id);
//    }
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public void updateMemberDirectly(Member member) {
        memberRepository.save(member);
    }

    public Member findByMemberEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION)
        );
    }
}
