package com.ssafy.dmobile.visit.controller;

import com.ssafy.dmobile.achieve.entity.Achieve;
import com.ssafy.dmobile.achieve.entity.AchieveMember;
import com.ssafy.dmobile.achieve.service.AchieveMemberService;
import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
import com.ssafy.dmobile.visit.entity.MemberRelic;
import com.ssafy.dmobile.visit.entity.dto.MemberRelicResponseDto;
import com.ssafy.dmobile.visit.service.MemberRelicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visit")
@Tag(name = "Visit And Achievements", description = "문화재 방문처리 및 업적 조회 API Document")
public class VisitedController {

    private final AuthTokensGenerator authTokensGenerator;
    private final MemberRelicService memberRelicService;
    private final AchieveMemberService achieveMemberService;

//    @PostMapping("/test")
//    @Operation(summary = "테스트 업로드용 API", description = "특정 키워드로 검색한 DetailData 일괄로 분석해서 relicId만 방문하도록 처리")
//    public List<Long> getTestValue(
//            @RequestBody List<DetailData> dataList) {
//
//        List<Long> list = new ArrayList<>();
//        for (DetailData info : dataList) {
//            list.add(info.getRelicId());
//        }
//        return list;
//    }
    
    @PostMapping("/{relicId}")
    @Operation(summary = "문화재 방문 처리", description = "사용자의 Token을 받아 해당 사용자의 방문 테이블에 relicId 방문처리")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updateVisitInfo(
            @PathVariable("relicId") Long relicId,
            @RequestHeader("Authorization") String token) {

        Long memberId = authTokensGenerator.extractMemberId(token);

        memberRelicService.visitSpecificSpot(memberId, relicId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulk-add")
    @Operation(summary = "문화재 일괄 방문 처리", description = "사용자의 Token을 받아 해당 사용자의 방문 테이블에 relicId 방문처리")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updateVisitInfo(
            @RequestHeader("Authorization") String token,
            @RequestBody List<Long> relicIds) {

        Long memberId = authTokensGenerator.extractMemberId(token);

        memberRelicService.visitSpecificSpotList(memberId, relicIds);

        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/list")
    @Operation(summary = "문화재 방문 리스트 조회", description = "사용자의 Token을 받아 해당 사용자의 문화재 방문 리스트 조회")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> getVisitedRelicList(
            @RequestHeader("Authorization") String token) {

        Long memberId = authTokensGenerator.extractMemberId(token);

        List<MemberRelic> list = memberRelicService.getVisitedRelicsInMember(memberId);
        List<MemberRelicResponseDto> response = new ArrayList<>();

        for (MemberRelic info : list) {

            MemberRelicResponseDto dto = MemberRelicResponseDto.builder()
                    .relicId(info.getDetailData().getRelicId())
                    .ccbaMnm1(info.getDetailData().getCcbaMnm1())
                    .visitedContent(info.getVisitedContent())
                    .visitedImage(info.getVisitedImage())
                    .visitedCreateTime(info.getVisitedCreateTime())
                    .build();

            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/achieve")
    @Operation(summary = "획득한 업적 내역 조회", description = "")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> getAchieveList(
            @RequestHeader("Authorization") String token) {

        Long memberId = authTokensGenerator.extractMemberId(token);

        List<AchieveMember> list = achieveMemberService.getAchievesInMember(memberId);
        List<Achieve> response = new ArrayList<>();

        for (AchieveMember info : list) {

            response.add(info.getAchieve());
        }

        return ResponseEntity.ok(response);
    }
}
