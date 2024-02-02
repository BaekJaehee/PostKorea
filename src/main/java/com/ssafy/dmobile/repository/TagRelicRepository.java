package com.ssafy.dmobile.repository;

import com.ssafy.dmobile.entity.DetailData;
import com.ssafy.dmobile.entity.ListData;
import com.ssafy.dmobile.entity.TagRelic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRelicRepository extends JpaRepository<TagRelic, Integer> {
//    List<TagRelic> findByTag(List<String> tag); // 태그로 리스트의 사항 찾기

    @Query("SELECT tr.detailData FROM TagRelic tr JOIN tr.tag t WHERE t.tagName IN :tags")
    List<DetailData> findByTag(@Param("tags") List<String> tags);

    // 이거 지금 jpql인데 querydsl로 고쳐야함 근데 지금도힘든데 querydsl 너무어렵다 q클래스가뭔데
//    @Query("SELECT DISTINCT tr.listData FROM TagRelic tr WHERE tr.tag.tagName IN :tagNames GROUP BY tr.listData HAVING COUNT(tr.listData) = :tagCount")    List<ListData> findListDataByTagNames(@Param("tagNames") List<String> tagNames);

//    @Query("SELECT DISTINCT tr.listData FROM TagRelic tr WHERE tr.tag.tagName IN :tagNames GROUP BY tr.listData HAVING COUNT(tr.listData) = :tagCount")
//    List<ListData> findListDataByTagNames(@Param("tagNames") List<String> tagNames, @Param("tagCount") Long tagCount);


//    @Query("SELECT tr.listData FROM TagRelic tr WHERE tr.tag.tagName = :ccceNameTag")
//    List<ListData> findByListDataCcceNameTag(String ccceNameTag);
//    @Query("SELECT tr.listData FROM TagRelic tr WHERE tr.tag.tagName = :ccbaLcadTag")
//    List<ListData> findByListDataCcbaLcadTag(String ccbaLcadTag);
//    @Query("SELECT tr.listData FROM TagRelic tr WHERE tr.tag.tagName = :mcodeNameTag")
//    List<ListData> findByListDataMcodeNameTag(String mcodeNameTag);
//    @Query("SELECT tr.listData FROM TagRelic tr WHERE tr.tag.tagName = :scodeNameTag")
//    List<ListData> findByListDataScodeNameTag(String scodeNameTag);
////    @Query("SELECT ld FROM ListData ld")
//    @Query("SELECT DISTINCT tr.listData FROM TagRelic tr")
//    List<ListData> findAllListData();

}
