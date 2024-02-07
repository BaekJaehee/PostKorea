package com.ssafy.dmobile.theme.dto.response;

import com.ssafy.dmobile.theme.entity.Theme;
import com.ssafy.dmobile.theme.entity.ThemeRelic;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ThemeResponseDTO {
    private Long themeId;
    private String themeName;
    private String description;
    private Set<Long> relicIds;

    public ThemeResponseDTO(Theme theme) {
        this.themeId = theme.getThemeId();
        this.themeName = theme.getThemeName();
        this.description = theme.getDescription();
        this.relicIds = theme.getThemeRelics().stream()
                .map(ThemeRelic::getDetailData)
                .map(detailData -> detailData.getRelicId())
                .collect(Collectors.toSet());
    }
}
