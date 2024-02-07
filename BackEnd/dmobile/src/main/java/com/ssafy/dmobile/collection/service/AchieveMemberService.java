package com.ssafy.dmobile.collection.service;

import com.ssafy.dmobile.collection.entity.achievement.Achieve;
import com.ssafy.dmobile.collection.entity.achievement.AchieveMember;
import com.ssafy.dmobile.collection.repository.achieve.AchieveMemberRepository;
import com.ssafy.dmobile.collection.repository.achieve.AchieveRepository;
import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchieveMemberService {

    public final MemberRepository memberRepository;
    public final AchieveRepository achieveRepository;
    public final AchieveMemberRepository achieveMemberRepository;

    public AchieveMember addAchieveInMember(Long memberId, Long achieveId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));
        Achieve achieve = achieveRepository.findById(achieveId)
                .orElseThrow(() -> new CustomException(ExceptionType.ACHIEVE_NOT_FOUND_EXCEPTION));

        AchieveMember achieveMember = new AchieveMember();
        achieveMember.setMember(member);
        achieveMember.setAchieve(achieve);

        return achieveMemberRepository.save(achieveMember);
    }
}
