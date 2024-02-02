package com.ssafy.dmobile.relic.repository;

import com.ssafy.dmobile.relic.entity.DetailData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailDataRepository extends JpaRepository<DetailData, Long> {
    DetailData findByItemId(Long listId);

//    @Query("SELECT dd FROM DetailData dd " +
//        "JOIN dd.tags t " +
//        "WHERE t.tagName IN :tagNames " +
//        "AND (dd.ccceName IN :tagNames OR dd.mcodeName IN :tagNames OR dd.scodeName IN :tagNames OR dd.ccbaLcad IN :tagNames)")
//    List<DetailData> findByTagNameIn(@Param("tagNames") List<String> tagNames);

}
