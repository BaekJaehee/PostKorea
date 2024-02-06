package com.ssafy.dmobile.theme.repository;

import com.ssafy.dmobile.theme.entity.ThemeRelic;
import com.ssafy.dmobile.theme.entity.ThemeRelicKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRelicRepository extends JpaRepository<ThemeRelic, ThemeRelicKey> {
}
