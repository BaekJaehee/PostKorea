package com.ssafy.dmobile.repository;

import com.ssafy.dmobile.entity.ListData;
import com.ssafy.dmobile.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListDataRepository extends JpaRepository<ListData, Integer> {
//    List<ListData> findByTagName(List<Tag> tag);
}
