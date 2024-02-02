package com.ssafy.dmobile.relic.repository;

import com.ssafy.dmobile.relic.entity.ListData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ListDataRepository extends JpaRepository<ListData, Long> {
    @Query("SELECT ld FROM ListData ld")
    List<ListData> findListDataByLimit(Pageable pageable);
}
