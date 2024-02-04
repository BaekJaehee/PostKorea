package com.ssafy.dmobile.relic.repository;

import com.ssafy.dmobile.relic.entity.DetailData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetailDataRepository extends JpaRepository<DetailData, Long> {
    DetailData findByItemId(Long itemId);

    @Query("SELECT d FROM DetailData d " +
            "WHERE (:region1 IS NULL OR d.region1 = :region1) " +
            "AND (:region2 IS NULL OR d.region2 = :region2) " +
            "AND (:ccceName IS NULL OR d.ccceName = :ccceName) " +
            "AND (:scodeName IS NULL OR d.scodeName = :scodeName)")
    List<DetailData> findbyTags(
            @Param("region1") String region1,
            @Param("region2") String region2,
            @Param("ccceName") String ccceName,
            @Param("scodeName") String scodeName
    );

    @Query("SELECT d.relicId, d.imageUrl, d.ccbaMnm1 FROM DetailData d")
    List<Object[]> findDataByLimit(Pageable pageable);
}
