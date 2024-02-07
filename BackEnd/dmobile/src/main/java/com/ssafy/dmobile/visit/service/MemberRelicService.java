package com.ssafy.dmobile.visit.service;

import com.ssafy.dmobile.achieve.service.AchieveMemberService;
import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
import com.ssafy.dmobile.visit.entity.MemberRelic;
import com.ssafy.dmobile.visit.entity.MemberRelicKey;
import com.ssafy.dmobile.visit.entity.SidoAchieveMappingInfo;
import com.ssafy.dmobile.visit.repository.MemberRelicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberRelicService {

    private final DetailDataRepository detailDataRepository;
    private final MemberRepository memberRepository;
    private final MemberRelicRepository memberRelicRepository;

    // Achieve 관련 파트는 Achieve-Service-Logic 처리
    private final AchieveMemberService achieveMemberService;
//    private final AchieveRepository achieveRepository;
//    private final AchieveMemberRepository achieveMemberRepository;

    public List<MemberRelic> getVisitedRelicsInMember(Long memberId) {
        return memberRelicRepository.findByMemberId(memberId);
    }

    @Transactional
    public void visitSpecificSpotList(Long planId, List<Long> relicIds) {

        for(Long relicId : relicIds) {
            visitSpecificSpot(planId, relicId);
        }
    }

    // TODO: Visit의 나머지 컬럼 (제목, 내용, 이미지) 추가 필요
    @Transactional
    public void visitSpecificSpot(Long memberId, Long relicId) {

        // 해당 문화재 방문처리 (현재 시간 기준)
        MemberRelic memberRelicInfo = saveVisitInfo(memberId, relicId);

        // 시도코드
        String sidoCode = memberRelicInfo.getDetailData().getCcbaCtcd();
        // 종목코드
        String relicTypeCode = memberRelicInfo.getDetailData().getCcbaKdcd();

        int sidoCount = memberRelicRepository.countVisitedCtcd(memberId, sidoCode);
        int relicTypeCount = memberRelicRepository.countVisitedKdcd(memberId, relicTypeCode);

        // sidoCount, relicTypeCount 를 기반으로 Achieve 갱신
        checkAndAssignAchieveInMember(memberId, sidoCode, sidoCount, relicTypeCode, relicTypeCount);
    }

    // member_id의 방문 목록에 relic_id를 지닌 문화재를 추가
    private MemberRelic saveVisitInfo(Long memberId, Long relicId) {

        MemberRelicKey memberRelicKey = new MemberRelicKey();
        memberRelicKey.setMemberId(memberId);
        memberRelicKey.setRelicId(relicId);

        MemberRelic memberRelic = new MemberRelic();
        memberRelic.setKey(memberRelicKey);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));
        DetailData detailData = detailDataRepository.findByItemId(relicId);

        memberRelic.setMember(member);
        memberRelic.setDetailData(detailData);
        memberRelic.setVisitedCreateTime(new Date().getTime());

        return memberRelicRepository.save(memberRelic);
    }

    // 다녀온 유적의 code에 따른 count를 가지고 특정 achieve가 달성되었는지 확인, 업데이트
    private void checkAndAssignAchieveInMember(Long memberId, String sidoCode, int currentSidoCount, String relicTypeCode, int currentRelicTypeCount) {

        // 시도 코드에 매핑된 업적 ID를 조회
        SidoAchieveMappingInfo mapping = SidoAchieveMappingInfo.findBySidoCode(sidoCode);

        Long[] achieveIds = new Long[4];
        for (int i = 0; i < 4; i++) {
            achieveIds[i] = (long) mapping.getAchieveIds()[i];
        }

        // 방문 횟수에 따른 업적 인덱스 매핑
        Map<Integer, Integer> countToIndexMap = Map.of(
                1, 0,
                10, 1,
                30, 2,
                50, 3
        );

        // 현재 방문 횟수에 해당하는 업적 인덱스 찾기
        Integer idx = countToIndexMap.get(currentSidoCount);
        if (idx != null) {
            achieveMemberService.addAchieveInMember(memberId, achieveIds[idx]);
        }
    }

//    // TODO: 칭호 달성 조건 검사 및 칭호 할당 로직 구현
//    private void checkAndAssignTitles(Long memberId, long visitCount) {
//
//    }
}



