package com.ssafy.dmobile.theme.service;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
import com.ssafy.dmobile.theme.dto.request.ThemeRequestDTO;
import com.ssafy.dmobile.theme.dto.response.ThemeResponseDTO;
import com.ssafy.dmobile.theme.entity.Theme;
import com.ssafy.dmobile.theme.entity.ThemeRelic;
import com.ssafy.dmobile.theme.entity.ThemeRelicKey;
import com.ssafy.dmobile.theme.repository.ThemeRelicRepository;
import com.ssafy.dmobile.theme.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService{
    private final ThemeRepository themeRepository;
    private final DetailDataRepository detailDataRepository;
    private final ThemeRelicRepository themeRelicRepository;

    @Override
    public List<ThemeResponseDTO> findAllThemes() {
        return themeRepository.findAll().stream()
                .map(theme -> new ThemeResponseDTO(theme.getThemeId(), theme.getThemeName(), theme.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ThemeResponseDTO addRelicToTheme(Long themeId, Long relicId) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Theme not found"));
        DetailData detailData = detailDataRepository.findById(relicId)
                .orElseThrow(() -> new RuntimeException("Relic not found"));

        ThemeRelic themeRelic = new ThemeRelic(new ThemeRelicKey(themeId, relicId), theme, detailData);
        theme.getThemeRelics().add(themeRelic);
        themeRepository.save(theme);

        return new ThemeResponseDTO(theme.getThemeId(), theme.getThemeName(), theme.getDescription());
    }

    @Override
    @Transactional
    public ThemeResponseDTO removeRelicFromTheme(Long themeId, Long relicId) {
        // 테마 엔티티에서 해당 문화재를 제거하는 로직 구현
        Theme theme = themeRepository.findById(themeId).orElseThrow(() ->
                new CustomException(ExceptionType.THEME_NOT_FOUND_EXCEPTION)
        );
        DetailData relic = detailDataRepository.findById(relicId).orElseThrow(() ->
                new CustomException(ExceptionType.RELIC_NOT_FOUND_EXCEPTION)
        );

        // 테마와 문화재의 연관 관계를 끊음
        ThemeRelicKey themeRelicKey = new ThemeRelicKey(themeId, relicId);
        ThemeRelic themeRelic = themeRelicRepository.findById(themeRelicKey).orElse(null);
        if (themeRelic != null) {
            theme.getThemeRelics().remove(themeRelic);
            themeRelicRepository.delete(themeRelic);
        }

        // 업데이트된 테마 엔티티를 DTO로 변환하여 반환
        Theme updatedTheme = themeRepository.save(theme);
        return new ThemeResponseDTO(updatedTheme.getThemeId(), updatedTheme.getThemeName(), updatedTheme.getDescription());
    }

    @Override
    @Transactional
    public ThemeResponseDTO createEmptyTheme(ThemeRequestDTO dto) {
        Theme theme = dto.dtotoEntity(dto);
        Theme save = themeRepository.save(theme);
        return new ThemeResponseDTO(save);
    }

    @Override
    @Transactional
    public void deleteTheme(Long themeId) {
        themeRepository.deleteById(themeId);
    }

    // 단건 조회
    @Override
    @Transactional(readOnly = true)
    public ThemeResponseDTO getThemeById(Long themeId) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new CustomException(ExceptionType.THEME_NOT_FOUND_EXCEPTION));
        return new ThemeResponseDTO(theme.getThemeId(), theme.getThemeName(), theme.getDescription());
    }

    @Override
    @Transactional
    public ThemeResponseDTO updateTheme(Long themeId, String themeName, String description) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new CustomException(ExceptionType.THEME_NOT_FOUND_EXCEPTION));

        theme.setThemeName(themeName);
        theme.setDescription(description);
        Theme updatedTheme = themeRepository.save(theme);

        return new ThemeResponseDTO(updatedTheme.getThemeId(), updatedTheme.getThemeName(), updatedTheme.getDescription());
    }

    @Override
    @Transactional
    public ThemeResponseDTO updateThemeWithRelic(Long themeId, Long relicId, boolean addRelic) {
        // 테마와 문화재 엔티티 찾기
        Theme theme = themeRepository.findById(themeId).orElseThrow(() ->
                new CustomException(ExceptionType.THEME_NOT_FOUND_EXCEPTION)
        );
        DetailData relic = detailDataRepository.findById(relicId).orElseThrow(() ->
                new CustomException(ExceptionType.RELIC_NOT_FOUND_EXCEPTION)
        );

        // 테마와 문화재의 연관 관계를 추가 또는 제거
        ThemeRelicKey themeRelicKey = new ThemeRelicKey(themeId, relicId);
        ThemeRelic themeRelic = themeRelicRepository.findById(themeRelicKey).orElse(null);

        if (addRelic) {
            if (themeRelic == null) {
                themeRelic = new ThemeRelic(new ThemeRelicKey(themeId, relicId), theme, relic);
                theme.getThemeRelics().add(themeRelic);
                themeRelicRepository.save(themeRelic);
            }
        } else {
            if (themeRelic != null) {
                theme.getThemeRelics().remove(themeRelic);
                themeRelicRepository.delete(themeRelic);
            }
        }

        // 업데이트된 테마 엔티티를 DTO로 변환하여 반환
        Theme updatedTheme = themeRepository.save(theme);
        return new ThemeResponseDTO(updatedTheme.getThemeId(), updatedTheme.getThemeName(), updatedTheme.getDescription());
    }
}
