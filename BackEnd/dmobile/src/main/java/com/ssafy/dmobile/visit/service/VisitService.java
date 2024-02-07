package com.ssafy.dmobile.visit.service;

import com.ssafy.dmobile.collection.repository.achieve.AchieveMemberRepository;
import com.ssafy.dmobile.collection.repository.achieve.AchieveRepository;
import com.ssafy.dmobile.collection.repository.title.TitleRepository;
import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
import com.ssafy.dmobile.visit.entity.Visit;
import com.ssafy.dmobile.visit.entity.VisitKey;
import com.ssafy.dmobile.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final DetailDataRepository detailDataRepository;
    private final AchieveRepository achieveRepository;
    private final AchieveMemberRepository achieveMemberRepository;
    private final MemberRepository memberRepository;
    private final TitleRepository titleRepository;
    private final VisitRepository visitRepository;

    // 특정 문화재 방문 시, achieve 및 title 을 변경하는 메서드 자동 실행
    public void visitSpecificSpot(Long memberId, Long relicId) {

        // 해당 문화재 방문처리 (현재 시간 기준)
        Visit visitInfo = saveVisit(memberId, relicId);

        // 시도코드
        String sidoCode = visitInfo.getDetailData().getCcbaCtcd();
        // 종목코드
        String relicTypeCode = visitInfo.getDetailData().getCcbaKdcd();

        int sidoCount = visitRepository.countVisitedCtcd(memberId, sidoCode);
        int relicTypeCount = visitRepository.countVisitedKdcd(memberId, relicTypeCode);

        // sidoCount, relicTypeCount 를 기반으로 Achieve 갱신
        checkAndAssignAchievements(memberId, sidoCode, sidoCount, relicTypeCode, relicTypeCount);
    }

    // member_id의 방문 목록에 relic_id를 지닌 문화재를 추가
    private Visit saveVisit(Long memberId, Long relicId) {

        VisitKey visitKey = new VisitKey();
        visitKey.setMemberId(memberId);
        visitKey.setRelicId(relicId);

        Visit visit = new Visit();
        visit.setKey(visitKey);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));
        DetailData detailData = detailDataRepository.findByItemId(relicId);

        visit.setMember(member);
        visit.setDetailData(detailData);
        visit.setVisitedCreateTime(new Date().getTime());

        return visitRepository.save(visit);
    }

    // TODO: 업적 달성 조건 검사 및 업적 할당 로직 구현
    private void checkAndAssignAchievements(Long memberId, String sidoCode, int sidoCount, String relicTypeCode, int relicTypeCount) {

    }

    // TODO: 칭호 달성 조건 검사 및 칭호 할당 로직 구현
    private void checkAndAssignTitles(Long memberId, long visitCount) {

    }
}



