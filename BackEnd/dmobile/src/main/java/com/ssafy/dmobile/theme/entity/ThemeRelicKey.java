package com.ssafy.dmobile.theme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ThemeRelicKey implements Serializable {

    @Column(name = "theme_id")
    private Long themeId;

    @Column(name = "relic_id")
    private Long relicId;
}
