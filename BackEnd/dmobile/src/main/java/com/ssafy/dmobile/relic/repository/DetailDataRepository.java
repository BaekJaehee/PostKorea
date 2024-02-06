package com.ssafy.dmobile.relic.repository;

import com.ssafy.dmobile.relic.entity.DetailData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetailDataRepository extends JpaRepository<DetailData, Long> {
    // id로 상세보기
    DetailData findByItemId(Long itemId);

    // 조건검색
    @Query("SELECT d FROM DetailData d " +
        "WHERE (:region1 IS NULL OR (d.region1 = :region1 OR d.region1 = :mappingRegion1)) " +
        "AND (:region2 IS NULL OR d.region2 = :region2) " +
        "AND (:ccceName IS NULL OR d.ccceName LIKE CONCAT('%', :ccceName, '%')) " +
        "AND (:mcodeName IS NULL OR d.mcodeName = :mcodeName)")
        List<DetailData> findbyTags(
            @Param("region1") String region1,
            @Param("mappingRegion1") String mappingRegion1,
            @Param("region2") String region2,
            @Param("ccceName") String ccceName,
            @Param("mcodeName") String mcodeName
        );

    // limit
    @Query("SELECT d FROM DetailData d")
    List<DetailData> findDataByLimit(Pageable pageable);
}
