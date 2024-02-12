package com.ssafy.dmobile.achievements.controller;

import com.ssafy.dmobile.achievements.entity.achieve.Achieve;
import com.ssafy.dmobile.achievements.entity.achieve.AchieveMember;
import com.ssafy.dmobile.achievements.service.AchieveMemberService;
import com.ssafy.dmobile.member.service.MemberService;
import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
import com.ssafy.dmobile.achievements.entity.visit.MemberRelic;
import com.ssafy.dmobile.achievements.entity.visit.dto.MemberAchieveResponseDto;
import com.ssafy.dmobile.achievements.entity.visit.dto.MemberRelicResponseDto;
import com.ssafy.dmobile.achievements.service.MemberRelicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visit")
@Tag(name = "Visit And Achievements", description = "문화재 방문처리 및 업적 조회 API Document")
public class AchievementsController {

    private final AuthTokensGenerator authTokensGenerator;
    private final MemberRelicService memberRelicService;
    private final AchieveMemberService achieveMemberService;
    private final MemberService memberService;

//    @PostMapping("/test")
//    @Operation(summary = "테스트 조회 API", description = "특정 키워드로 검색한 DetailData 리스트를 분석해서 relicId 만 추출하는 API, 배포시 주석처리 혹은 삭제")
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
    @Operation(summary = "획득한 업적 내역 조회", description = "사용자의 토큰을 받아 해당 사용자의 업적 달성내역을 조회합니다." +
            "<br>owned 파라미터에 대해 true 입력 시 보유한 업적 조회, false 입력 시 보유하지 않은 업적 조회 (미입력시 보유한 것 조회)")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> getAchieveList(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false, defaultValue = "true") boolean owned) {

        Long memberId = authTokensGenerator.extractMemberId(token);
        List<MemberAchieveResponseDto> response = new ArrayList<>();

        if (owned) {
            for(AchieveMember am : achieveMemberService.getAchieveMembers(memberId) ){
                MemberAchieveResponseDto dto = MemberAchieveResponseDto.builder()
                        .achieveId(am.getAchieve().getAchieveId())
                        .achieveName(am.getAchieve().getAchieveName())
                        .achieveDesc(am.getAchieve().getAchieveDesc())
                        .achieveDate(am.getAchieveDate())
                        .achieveRelicName(am.getRelicName()).build();

                response.add(dto);
            }
        } else {
            for(Achieve achieve : achieveMemberService.getAchievesNotInMember(memberId) ){
                MemberAchieveResponseDto dto = MemberAchieveResponseDto.builder()
                        .achieveId(achieve.getAchieveId())
                        .achieveName(achieve.getAchieveName())
                        .achieveDesc(achieve.getAchieveDesc()).build();

                response.add(dto);
            }
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/achieve-info/{achieveId}")
    @Operation(summary = "업적 상세 조회", description = "achieveId를 받아 해당 업적의 상세 정보를 출력합니다.")
    public ResponseEntity<?> getAchieveInfo(@PathVariable Long achieveId) {

        Achieve achieve = achieveMemberService.getAchieveInfo(achieveId);
        return ResponseEntity.ok(achieve);
    }

    @PutMapping("/title/{achieveId}")
    @Operation(summary = "맴버의 대표 칭호 설정", description = "사용자의 토큰을 받아 해당 사용자가 선택한 업적, 칭호를 설정합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updateTitleAchieve(
            @RequestHeader("Authorization") String token,
            @PathVariable Long achieveId) {

        Long memberId = authTokensGenerator.extractMemberId(token);

        memberService.editMemberTitleAchieve(memberId, achieveId);
        return ResponseEntity.ok().build();
    }
}
