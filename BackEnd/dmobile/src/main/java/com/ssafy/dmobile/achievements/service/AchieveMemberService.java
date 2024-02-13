package com.ssafy.dmobile.achievements.service;

import com.ssafy.dmobile.achievements.entity.achieve.Achieve;
import com.ssafy.dmobile.achievements.entity.achieve.AchieveMember;
import com.ssafy.dmobile.achievements.entity.achieve.AchieveMemberKey;
import com.ssafy.dmobile.achievements.entity.mapper.RelicTypeMappingInfo;
import com.ssafy.dmobile.achievements.entity.mapper.SidoAchieveMappingInfo;
import com.ssafy.dmobile.achievements.entity.visit.dto.MemberAchieveResponseDto;
import com.ssafy.dmobile.achievements.repository.AchieveMemberRepository;
import com.ssafy.dmobile.achievements.repository.AchieveRepository;
import com.ssafy.dmobile.achievements.repository.MemberRelicRepository;
import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AchieveMemberService {

    public final MemberRepository memberRepository;
    public final MemberRelicRepository memberRelicRepository;
    public final DetailDataRepository detailDataRepository;
    public final AchieveRepository achieveRepository;
    public final AchieveMemberRepository achieveMemberRepository;

    // 플래아어에게 업적 추가
    public void addAchieveInMember(Long memberId, Long achieveId, Long relicId) {

        AchieveMemberKey key = new AchieveMemberKey();
        key.setMemberId(memberId);
        key.setAchieveId(achieveId);

        AchieveMember achieveMember = new AchieveMember();
        achieveMember.setKey(key);
        achieveMember.setAchieve(achieveRepository.getReferenceById(achieveId));
        achieveMember.setMember(memberRepository.getReferenceById(memberId));
        achieveMember.setAchieveDate(new Date().getTime());
        achieveMember.setRelicName(detailDataRepository.getReferenceById(relicId).getCcbaMnm1());

        achieveMemberRepository.save(achieveMember);
    }

    // 개별 업적의 정보 조회
    public MemberAchieveResponseDto getAchieveInfoInMember(Long achieveId, Long memberId) {

        AchieveMemberKey key = new AchieveMemberKey();
        key.setMemberId(memberId);
        key.setAchieveId(achieveId);

        AchieveMember am = achieveMemberRepository.getReferenceById(key);
        Achieve achieve = am.getAchieve();

        if (achieveId < 69) {
            String sidoCode = SidoAchieveMappingInfo.findSidoCodeByAchieveId(achieveId);

            // 해당 sidoCode를 지닌 relic의 보유갯수 가져오기
            int cnt = memberRelicRepository.countVisitedCtcd(memberId, sidoCode);

            Map<Integer, Integer> achieveIdToCount = Map.of(
                    1, 1,
                    2, 10,
                    3, 30,
                    4, 50
            );
            // achieve의 취득 조건과 비교
            int percent = cnt / achieveIdToCount.get(achieveId.intValue() % 4) * 100;
            if(percent > 100) percent = 100;

            return MemberAchieveResponseDto.builder()
                    .achieveName(achieve.getAchieveName())
                    .achieveDesc(achieve.getAchieveDesc())
                    .achieveDate(am.getAchieveDate())
                    .achieveRelicName(am.getRelicName())
                    .achievePercentage(percent).build();
        } else {
            String relicTypeCode = RelicTypeMappingInfo.findRelicTypeCodeByAchieveId(achieveId);

            int cnt = memberRelicRepository.countVisitedKdcd(memberId, relicTypeCode);

            Map<Integer, Integer> achieveIdToCount = Map.of(
                    1, 1,
                    2, 10,
                    3, 30,
                    4, 50
            );
            // 4. 해당 achieve의 취득조건 가져오기
            int percent = cnt / achieveIdToCount.get(achieveId.intValue() % 4) * 100;
            if(percent > 100) percent = 100;

            return MemberAchieveResponseDto.builder()
                    .achieveName(achieve.getAchieveName())
                    .achieveDesc(achieve.getAchieveDesc())
                    .achieveDate(am.getAchieveDate())
                    .achieveRelicName(am.getRelicName())
                    .achievePercentage(percent).build();
        }
    }

    // 플레이어가 보유한 업적 조회
//    public List<Achieve> getAchievesInMember(Long memberId) {
//        return achieveMemberRepository.findByAchievesOwnedByMember(memberId);
//    }

    public List<AchieveMember> getAchieveMembers(Long memberId) {
        return achieveMemberRepository.findByMemberId(memberId);
    }

    // 플레이어가 보유하지 않은 업적 조회
    public List<Achieve> getAchievesNotInMember(Long memberId) {
        return achieveMemberRepository.findByAchievesNotOwnedByMember(memberId);
    }
    
}
