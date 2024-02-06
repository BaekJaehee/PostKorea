package com.ssafy.dmobile.theme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class ThemeRelic {

    @Id @GeneratedValue
    @Column(name = "theme_relic_id")
    private  Long id;

    @Column(name = "theme_id")
    private Long themeId;

    @Column(name = "relic_id")
    private Long relicId;
}
