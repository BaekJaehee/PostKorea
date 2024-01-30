package com.ssafy.dmobile.repository;

import com.ssafy.dmobile.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
//    List<Tag> findByName(List<String> tagName);
}
