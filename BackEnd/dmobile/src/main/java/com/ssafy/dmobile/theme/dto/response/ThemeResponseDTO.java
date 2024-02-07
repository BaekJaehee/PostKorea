package com.ssafy.dmobile.theme.dto.response;

import com.ssafy.dmobile.theme.entity.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ThemeResponseDTO {
    private Long themeId;
    private String themeName;
    private String description;

    public ThemeResponseDTO(Theme theme) {
        this.themeId = theme.getThemeId();
        this.themeName = theme.getThemeName();
        this.description = theme.getDescription();
    }
}
