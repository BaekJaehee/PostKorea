package com.ssafy.dmobile.repository;

import com.ssafy.dmobile.entity.DetailData;
import com.ssafy.dmobile.entity.ListData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailDataRepository extends JpaRepository<DetailData, Integer> {
    DetailData findByListId(Integer listId);

    List<DetailData> findByTagIn(List<String> tagNames);
//    DetailData findByListId(ListData listData);
}
