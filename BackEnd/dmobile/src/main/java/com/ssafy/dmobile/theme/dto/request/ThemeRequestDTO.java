package com.ssafy.dmobile.theme.dto.request;

import com.ssafy.dmobile.theme.entity.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeRequestDTO {
    private Long themeId;
    private String themeName;
    private String description;

    public Theme dtoToEntity(ThemeRequestDTO dto) {
        return Theme.builder()
                .themeName(dto.getThemeName())
                .description(dto.getDescription())
                .build();
    }
}
