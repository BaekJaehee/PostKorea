package com.ssafy.dmobile.relic.repository;

import com.ssafy.dmobile.relic.entity.ListData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ListDataRepository extends JpaRepository<ListData, Long> {
    @Query("SELECT ld FROM ListData ld")
//    @Query("SELECT ld, dd.imageUrl FROM ListData ld JOIN ld.DetailData dd WHERE ld.itemId = :itemId")
//     listdata에 detaildata 이미지 주소 join하기
    List<ListData> findListDataByLimit(Pageable pageable);

//    @Query("SELECT ld, dd.imageUrl FROM ListData ld JOIN ld.detailData dd WHERE dd.itemId = ld.itemId")
//    @Query("SELECT new com.ssafy.dmobile.relic.dto.ListDataDto(ld, dd.imageUrl) FROM ListData ld JOIN ld.detailData dd WHERE dd.itemId = ld.itemId")
//    List<Object[]> findListDataImageUrl(Pageable pageable);


//    @Query("SELECT ld, dd.imageUrl FROM ListData ld JOIN ld.detailData dd WHERE dd.itemId = ld.itemId")
//    @Query("SELECT new com.example.ListDataDTO(ld, dd.imageUrl) FROM ListData ld JOIN ld.detailData dd WHERE dd.itemId = ld.itemId")
//@Query("SELECT new com.ssafy.dmobile.relic.dto.ListDataDto(ld, dd.imageUrl) FROM ListData ld JOIN DetailData dd ON dd.itemId = ld.item_id")
//    List<ListDataDto> findListDataImageUrl(Pageable pageable);



//    @Query("SELECT new map(ld.itemId as itemId, dd.imageUrl as imageUrl) FROM ListData ld JOIN ld.detailDataList dd WHERE ld.itemId = :itemId")
//    List<Map<String, Object>> findImageUrlsByItemIdMap(@Param("itemId") Long itemId, Pageable pageable);

//    @Query("SELECT ld FROM ListData ld JOIN ld.detailData dd WHERE dd.itemId = :itemId")
//    public List<ListData> findImageUrlsByItemIdMap(@Param("itemId") Long itemId, Pageable pageable);

//    @Query("SELECT ld FROM ListData ld JOIN FETCH ld.detailDataList dd WHERE dd.itemId = :itemId")
//    public List<ListData> findImageUrlsByItemIdMap(@Param("itemId") Long itemId, Pageable pageable);

//    List<ListData> findImageUrlsByItemIds(List<Long> itemIds);

//    @Query("SELECT ld FROM ListData ld JOIN ld.detailData dd WHERE ld.itemId IN :itemIds")
//    List<ListData> findListDataAndImageUrlsByItemIds(@Param("itemIds") List<Long> itemIds);
}
