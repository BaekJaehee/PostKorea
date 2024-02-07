package com.ssafy.dmobile.visit.repository;

import com.ssafy.dmobile.visit.entity.MemberRelic;
import com.ssafy.dmobile.visit.entity.MemberRelicKey;
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
