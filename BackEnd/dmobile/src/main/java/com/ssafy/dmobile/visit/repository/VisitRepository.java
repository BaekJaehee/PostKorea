package com.ssafy.dmobile.visit.repository;

import com.ssafy.dmobile.visit.entity.Visit;
import com.ssafy.dmobile.visit.entity.VisitKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VisitRepository extends JpaRepository<Visit, VisitKey> {

    @Query("select count(v) from Visit v " +
            "where v.member.id = :memberId " +
            "and v.detailData.ccbaCtcd = :ccbaCtcd")
    int countVisitedCtcd(@Param("memberId") Long memberId, @Param("ccbaCtcd") String ccbaCtcd);

    @Query("select count(v) from Visit v " +
            "where v.member.id = :memberId " +
            "and v.detailData.ccbaKdcd = :ccbaKdcd")
    int countVisitedKdcd(@Param("memberId") Long memberId, @Param("ccbaKdcd") String ccbaKdcd);
}
