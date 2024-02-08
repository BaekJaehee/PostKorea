package com.ssafy.dmobile.relic.repository;

import com.ssafy.dmobile.relic.entity.DetailData;
import org.springframework.data.domain.PageRequest;
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
        "AND (:era IS NULL OR d.ccceName LIKE CONCAT('%', :era, '%')) " +
        "AND (:category IS NULL OR d.mcodeName = :category)")
        List<DetailData> findbyTags(
            @Param("region1") String region1,
            @Param("mappingRegion1") String mappingRegion1,
            @Param("region2") String region2,
            @Param("era") String era,
            @Param("category") String category,
            Pageable pageable
        );

    // limit
    @Query("SELECT d FROM DetailData d")
    List<DetailData> findDataByLimit(Pageable pageable);

    @Query("SELECT d FROM DetailData d WHERE d.ccbaMnm1 LIKE CONCAT('%', :name, '%') ")
    List<DetailData> findByName(String name);

//    @Query("SELECT d.imageUrl FROM DetailData d WHERE d.mcodeName = :category AND d.relicId != :id")
//    List<DetailData> getDataByCategory(String category, Long id);
    List<DetailData> findByMcodeNameAndRelicIdNot(String mcodeName, Long relicId);

    @Query("SELECT d.mcodeName FROM DetailData d WHERE d.relicId = :relicId")
    List<String> findTagById(Long relicId);

    @Query("SELECT d FROM DetailData d " +
            "WHERE (:region1 IS NULL OR (d.region1 = :region1 OR d.region1 = :mappingRegion1)) " +
            "AND (:region2 IS NULL OR d.region2 = :region2) " +
            "AND (:era IS NULL OR d.ccceName LIKE CONCAT('%', :era, '%')) " +
            "AND (:category IS NULL OR d.mcodeName = :category)")
    List<DetailData> findRandomly(
            @Param("region1") String region1,
            @Param("mappingRegion1") String mappingRegion1,
            @Param("region2") String region2,
            @Param("era") String era,
            @Param("category") String category
    );
}
