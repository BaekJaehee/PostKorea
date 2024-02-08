package com.ssafy.dmobile.achieve.service;

import com.ssafy.dmobile.achieve.entity.Achieve;
import com.ssafy.dmobile.achieve.entity.AchieveMember;
import com.ssafy.dmobile.achieve.entity.AchieveMemberKey;
import com.ssafy.dmobile.achieve.repository.AchieveMemberRepository;
import com.ssafy.dmobile.achieve.repository.AchieveRepository;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.visit.entity.MemberRelic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AchieveMemberService {

    public final MemberRepository memberRepository;
    public final AchieveRepository achieveRepository;
    public final AchieveMemberRepository achieveMemberRepository;

    public void addAchieveInMember(Long memberId, Long achieveId) {

        AchieveMemberKey key = new AchieveMemberKey();
        key.setMemberId(memberId);
        key.setAchieveId(achieveId);

        AchieveMember achieveMember = new AchieveMember();
        achieveMember.setKey(key);
        achieveMember.setAchieve(achieveRepository.getReferenceById(achieveId));
        achieveMember.setMember(memberRepository.getReferenceById(memberId));
        achieveMember.setAchieveDate(new Date().getTime());

        achieveMemberRepository.save(achieveMember);
    }

    public List<AchieveMember> getAchievesInMember(Long memberId) {
        return achieveMemberRepository.findByMemberId(memberId);
    }
}
