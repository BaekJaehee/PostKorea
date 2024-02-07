package com.ssafy.dmobile.theme.service;

import com.ssafy.dmobile.theme.dto.request.ThemeRequestDTO;
import com.ssafy.dmobile.theme.dto.response.ThemeResponseDTO;

import java.util.List;

public interface ThemeService {
    List<ThemeResponseDTO> findAllThemes();

    // 테마 하나 선택
    ThemeResponseDTO getThemeById(Long themeId);

//    ThemeResponseDTO getThemeById(Long themeId, ThemeResponseDTO dto);

    // 테마에 문화재 추가
    ThemeResponseDTO addRelicToTheme(Long themeId, Long relicId);
    // 테마에 문화재 삭제
    ThemeResponseDTO removeRelicFromTheme(Long themeId, Long relicId);
    // 빈 테마 생성
    ThemeResponseDTO createEmptyTheme(ThemeRequestDTO dto);
    // 테마 삭제
    void deleteTheme(Long themeId);

    // 태마 업데이트
    ThemeResponseDTO updateTheme(Long themeId, ThemeResponseDTO dto);
    // 테마에 문화재 추가
    ThemeResponseDTO updateThemeWithRelic(Long themeId, Long relicId, boolean addRelic);
}
