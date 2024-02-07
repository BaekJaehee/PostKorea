package com.ssafy.dmobile.theme.entity;

import com.ssafy.dmobile.relic.entity.DetailData;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "theme_relic")
public class ThemeRelic {

    @EmbeddedId
    private ThemeRelicKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("themeId")
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("relicId")
    @JoinColumn(name = "relic_id")
    private DetailData detailData;
}


