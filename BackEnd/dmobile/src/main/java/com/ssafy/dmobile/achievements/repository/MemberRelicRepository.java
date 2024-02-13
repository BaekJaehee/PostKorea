package com.ssafy.dmobile.achievements.repository;

import com.ssafy.dmobile.achievements.entity.visit.MemberRelic;
import com.ssafy.dmobile.achievements.entity.visit.MemberRelicKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRelicRepository extends JpaRepository<MemberRelic, MemberRelicKey> {

    @Query("select count(v) from MemberRelic v " +
            "where v.member.id = :memberId " +
            "and v.detailData.ccbaCtcd = :ccbaCtcd")
    int countVisitedCtcd(@Param("memberId") Long memberId, @Param("ccbaCtcd") String ccbaCtcd);

    @Query("select count(v) from MemberRelic v " +
            "where v.member.id = :memberId " +
            "and v.detailData.ccbaKdcd = :ccbaKdcd")
    int countVisitedKdcd(@Param("memberId") Long memberId, @Param("ccbaKdcd") String ccbaKdcd);

    List<MemberRelic> findByMemberId(Long memberId);
}
