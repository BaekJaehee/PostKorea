package com.ssafy.dmobile.theme.service;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
import com.ssafy.dmobile.theme.dto.RelicDetailDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService{
    private final ThemeRepository themeRepository;
    private final DetailDataRepository detailDataRepository;
    private final ThemeRelicRepository themeRelicRepository;

    @Override
    public List<ThemeResponseDTO> findAllThemes() {
        List<Theme> themes = themeRepository.findAll();
        List<ThemeResponseDTO> themeResponseDTOS = new ArrayList<>();

        for (Theme theme : themes) {
            themeResponseDTOS.add(new ThemeResponseDTO(theme));
        }
        return themeResponseDTOS;
    }

    @Override
    public ThemeResponseDTO getThemeById(Long themeId) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new CustomException(ExceptionType.THEME_NOT_FOUND_EXCEPTION));
        ThemeResponseDTO responseDTO = new ThemeResponseDTO(theme);

        // Relic 상세 정보 조회 및 설정
        Set<RelicDetailDTO> relicDetails = theme.getThemeRelics().stream()
                .map(ThemeRelic::getDetailData)
                .map(detailData -> new RelicDetailDTO(
                        detailData.getRelicId(),
                        detailData.getItemId(),
                        detailData.getCcmaName(),
                        detailData.getCcbaMnm1(),
                        detailData.getCcbaMnm2(),
                        detailData.getCcbaKdcd(),
                        detailData.getCcbaCtcd(),
                        detailData.getCcbaAsno(),
                        detailData.getCcbaCpno(),
                        detailData.getLongitude(),
                        detailData.getLatitude(),
                        detailData.getGcodeName(),
                        detailData.getBcodeName(),
                        detailData.getMcodeName(),
                        detailData.getScodeName(),
                        detailData.getCcbaQuan(),
                        detailData.getCcbaAsdt(),
                        detailData.getCcbaLcad(),
                        detailData.getCcceName(),
                        detailData.getCcbaPoss(),
                        detailData.getCcbaAdmin(),
                        detailData.getImageUrl(),
                        detailData.getContent(),
                        detailData.getRegion1(),
                        detailData.getRegion2()
                ))
                .collect(Collectors.toSet());

        responseDTO.setRelicDetails(relicDetails); // ThemeResponseDTO에 Relic 상세 정보 설정

        return responseDTO;
    }


    @Override
    @Transactional
    public ThemeResponseDTO addRelicToTheme(Long themeId, Long relicId) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new CustomException(ExceptionType.THEME_NOT_FOUND_EXCEPTION));
        DetailData detailData = detailDataRepository.findById(relicId)
                .orElseThrow(() -> new CustomException(ExceptionType.RELIC_NOT_FOUND_EXCEPTION));

        ThemeRelic themeRelic = new ThemeRelic(new ThemeRelicKey(themeId, relicId), theme, detailData);
        theme.getThemeRelics().add(themeRelic);
        themeRepository.save(theme);

        return new ThemeResponseDTO(theme);
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

        // 테마와 문화재의 연관 관계 찾아서 제거
        ThemeRelicKey themeRelicKey = new ThemeRelicKey(themeId, relicId);
        themeRelicRepository.findById(themeRelicKey).ifPresent(ThemeRelic -> {
            theme.getThemeRelics().remove(ThemeRelic);
            themeRelicRepository.delete(ThemeRelic);
        });
        Theme updatedTheme = themeRepository.save(theme);

        return new ThemeResponseDTO(updatedTheme);
    }

    // 빈 테마 생성(이름이랑 설명은 들어감)
    @Override
    @Transactional
    public ThemeResponseDTO createEmptyTheme(ThemeRequestDTO dto) {
        Theme theme = dto.dtoToEntity(dto);
        theme.setThemeName(theme.getThemeName());
        theme.setDescription(theme.getDescription());
        Theme save = themeRepository.save(theme);
        return new ThemeResponseDTO(save);
    }

    // 테마 삭제
    @Override
    @Transactional
    public void deleteTheme(Long themeId) {
        themeRepository.deleteById(themeId);
    }

    // 단건 조회
//    @Override
//    @Transactional(readOnly = true)
//    public ThemeResponseDTO getThemeById(Long themeId) {
//        Theme theme = themeRepository.findById(themeId)
//                .orElseThrow(() -> new CustomException(ExceptionType.THEME_NOT_FOUND_EXCEPTION));
//        return new ThemeResponseDTO(theme);
//    }

    @Override
    @Transactional
    public ThemeResponseDTO updateTheme(Long themeId, ThemeResponseDTO dto) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new CustomException(ExceptionType.THEME_NOT_FOUND_EXCEPTION));
        theme.update(dto.getThemeName(), dto.getDescription());
        Theme save = themeRepository.save(theme);
        return new ThemeResponseDTO(save);
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
        Optional<ThemeRelic> optionalThemeRelic = themeRelicRepository.findById(themeRelicKey);

        if (addRelic) {
            if (!optionalThemeRelic.isPresent()) {
                ThemeRelic themeRelic = new ThemeRelic(themeRelicKey, theme, relic);
                theme.getThemeRelics().add(themeRelic);
                themeRelicRepository.save(themeRelic);
            }
        } else {
            optionalThemeRelic.ifPresent(themeRelic -> {
                theme.getThemeRelics().remove(themeRelic);
                themeRelicRepository.delete(themeRelic);
            });
        }
        Theme updatedTheme = themeRepository.save(theme);
        return new ThemeResponseDTO(updatedTheme);
    }
}
