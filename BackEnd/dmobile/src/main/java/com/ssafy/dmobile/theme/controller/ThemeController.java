package com.ssafy.dmobile.theme.controller;

import com.ssafy.dmobile.theme.dto.request.ThemeRequestDTO;
import com.ssafy.dmobile.theme.dto.response.ThemeResponseDTO;
import com.ssafy.dmobile.theme.service.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
@Tag(name = "Theme", description = "테마 관련 API")
public class ThemeController {

    private final ThemeService themeService;
    private static final Logger logger = LoggerFactory.getLogger(ThemeController.class);

    // 테마 전체 조회
    @GetMapping
    @Operation(summary = "전체 테마 조회", description = "테마 전체를 조회하는 기능.")
    public ResponseEntity<List<ThemeResponseDTO>> getAllThemes() {
        List<ThemeResponseDTO> themes = themeService.findAllThemes();
        return new ResponseEntity<>(themes, HttpStatus.OK);
    }

    // 기존에 있는 테마 단건 조회
    @GetMapping("/{themeId}")
    @Operation(summary = "테마 조회", description = "테마 하나를 조회합니다.")
    public ResponseEntity<ThemeResponseDTO> getThemeById(@PathVariable Long themeId) {
        ThemeResponseDTO dto = themeService.getThemeById(themeId);

        return ResponseEntity.ok(dto);
    }



    // 테마에 문화재 추가
//    @PostMapping("/{themeId}/relics/{relicId}")
//    @Operation(summary = "테마에 문화재 추가", description = "테마에 문화재 하나를 추가합니다.")
//    // 여기부터 인증 필요한 지 마지막에 생각해 볼 것
//    public ResponseEntity<ThemeResponseDTO> addRelicToTheme(@PathVariable Long themeId, @PathVariable Long relicId) {
//        ThemeResponseDTO updatedTheme = themeService.addRelicToTheme(themeId, relicId);
//        return new ResponseEntity<>(updatedTheme, HttpStatus.OK);
//    }

    // 빈 테마 생성
//    @PostMapping
//    @Operation(summary = "빈 테마 등록", description = "빈 테마를 생성합니다.")
//    public ResponseEntity<ThemeResponseDTO> createEmptyTheme(@RequestBody ThemeRequestDTO themeRequestDTO) {
//        ThemeResponseDTO themeResponseDTO = themeService.createEmptyTheme(themeRequestDTO);
//        return new ResponseEntity<>(themeResponseDTO, HttpStatus.OK);
//    }

    // 테마 삭제
//    @DeleteMapping("/{themeId}")
//    @Operation(summary = "테마 삭제", description = "테마를 삭제합니다.")
//    public ResponseEntity<?> deleteTheme(@PathVariable Long themeId) {
//        themeService.deleteTheme(themeId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }



    // 테마 업데이트 기능 -> 테마의 제목, 설명을 업데이트 가능함.
//    @PutMapping("/{themeId}")
//    @Operation(summary = "테마 업데이트", description = "테마 이름, 설명을 업데이트 합니다.")
//    public ResponseEntity<ThemeResponseDTO> updateTheme(
//            @PathVariable Long themeId,
//            @RequestBody ThemeResponseDTO dto) {
//        logger.info("Updating themeId: {}, with themeName: {} and description: {}", themeId, dto.getThemeName(), dto.getDescription());
//        ThemeResponseDTO themeResponseDTO = themeService.updateTheme(themeId, dto);
//        return new ResponseEntity<>(themeResponseDTO, HttpStatus.OK);
//    }

    // 테마에 문화재 추가, 삭제 기능 : 문화재가 있으면 삭제하고, 없으면 추가한다.
//    @PutMapping("/{themeId}/relics/{relicId}")
//    @Operation(summary = "테마에 문화제 삭제, 추가", description = "테마에 문화재가 있으면 문화재를 삭제하고, " +
//            "<br> 문화재가 없으면 추가합니다.")
//    public ResponseEntity<ThemeResponseDTO> addOrRemoveRelicFromTheme(
//            @PathVariable Long themeId,
//            @PathVariable Long relicId,
//            @RequestParam boolean addRelic) {
//        ThemeResponseDTO updatedTheme = themeService.updateThemeWithRelic(themeId, relicId, addRelic);
//        return new ResponseEntity<>(updatedTheme, HttpStatus.OK);
//    }

    // 테마에서 문화재 제거
//    @DeleteMapping("/{themeId}/relics/{relicId}")
//    @Operation(summary = "테마에 문화재 삭제", description = "테마에서 문화재를 삭제합니다.")
//    public ResponseEntity<ThemeResponseDTO> removeRelicFromTheme(
//            @PathVariable Long themeId,
//            @PathVariable Long relicId) {
//        ThemeResponseDTO updatedTheme = themeService.removeRelicFromTheme(themeId, relicId);
//        return new ResponseEntity<>(updatedTheme, HttpStatus.OK);
//    }
}
