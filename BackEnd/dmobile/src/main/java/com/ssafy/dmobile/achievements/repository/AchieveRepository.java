package com.ssafy.dmobile.achievements.repository;

import com.ssafy.dmobile.achievements.entity.achieve.Achieve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchieveRepository extends JpaRepository<Achieve, Long> {
}
