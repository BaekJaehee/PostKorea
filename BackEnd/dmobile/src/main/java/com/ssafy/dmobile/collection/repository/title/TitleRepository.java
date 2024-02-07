package com.ssafy.dmobile.collection.repository.title;

import com.ssafy.dmobile.collection.entity.title.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long> {
}
