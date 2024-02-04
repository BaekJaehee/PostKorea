package com.ssafy.dmobile.explore.controller;

import com.ssafy.dmobile.explore.entity.ExplorePlan;
import com.ssafy.dmobile.explore.entity.dto.ExplorePlanDto;
import com.ssafy.dmobile.explore.service.ExplorePlanService;
import com.ssafy.dmobile.explore.service.RelicExplorePlanService;
import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("explore-plans")
@RequiredArgsConstructor
@Tag(name = "Explore Plan", description = "탐방 계획 API Document")
public class ExplorePlanController {

    private final ExplorePlanService explorePlanService;
    private final AuthTokensGenerator authTokensGenerator;

    @PostMapping
    @Operation(summary = "탐방 계획 생성", description = "이름, 시간 등의 정보를 받아 탐방 계획을 생성합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ExplorePlan> createPlan(
            @RequestHeader("Authorization") String token,
            @RequestBody ExplorePlanDto explorePlanDto) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        ExplorePlan newPlan = explorePlanService.createPlan(memberId, explorePlanDto);
        return ResponseEntity.ok(newPlan);
    }

    @PutMapping("/{planId}")
    @Operation(summary = "탐방 계획 수정", description = "이름, 시간 등의 정보를 받아 탐방 계획을 수정합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ExplorePlan> updatePlan(
            @RequestHeader("Authorization") String token,
            @PathVariable Long planId,
            @RequestBody ExplorePlanDto explorePlanDto) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        ExplorePlan updatedPlan = explorePlanService.updatePlan(planId, memberId, explorePlanDto);
        return ResponseEntity.ok(updatedPlan);
    }

    @DeleteMapping("/{planId}")
    @Operation(summary = "탐방 계획 삭제", description = "하당 아이디를 지닌 계획을 삭제합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Void> deletePlan(
            @RequestHeader("Authorization") String token,
            @PathVariable Long planId) {
        Long memberId = authTokensGenerator.extractMemberId(token); // 토큰에서 memberId 추출하는 로직 구현 필요
        explorePlanService.deletePlan(planId, memberId);
        return ResponseEntity.ok().build();
    }

    // DTO를 사용하면 좋으나, 시간관계상 List 그대로 출력 (변환은 Jackson 라이브러리 제공)
    @GetMapping("/ongoing")
    @Operation(summary = "진행중인 탐방", description = "현재 시간을 기준으로 진행중인 탐방을 출력합니다.")
    @SecurityRequirement(name = "Authorization")
    public List<ExplorePlan> getOngoingPlans(@RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token); // 토큰에서 memberId 추출하는 로직 구현 필요
        return explorePlanService.getOngoingPlans(memberId);
    }

    @GetMapping("/upcoming")
    @Operation(summary = "앞으로의 탐방", description = "현재 시간을 기준으로 계획중인 탐방을 출력합니다.")
    @SecurityRequirement(name = "Authorization")
    public List<ExplorePlan> getUpcomingPlans(@RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token); // 토큰에서 memberId 추출하는 로직 구현 필요
        return explorePlanService.getUpcomingPlans(memberId);
    }

    @GetMapping("/completed")
    @Operation(summary = "지금까지의 탐방", description = "현재 시간을 기준으로 지금까지 경험한 탐방을 출력합니다.")
    @SecurityRequirement(name = "Authorization")
    public List<ExplorePlan> getCompletedPlans(@RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token); // 토큰에서 memberId 추출하는 로직 구현 필요
        return explorePlanService.getCompletedPlans(memberId);
    }

    @GetMapping("/list/{planId}")
    @Operation(summary = "탐방 상세 내역 조회", description = "PlanId를 지닌 탐방 내 지금까지 다녀온 문화재 조회, 아직 미구현")
    @SecurityRequirement(name = "Authorization")
    public List<DetailData> getVisitedRelicList(
            @RequestHeader("Authorization") String token,
            @PathVariable Long planId) {

        List<DetailData> list = new ArrayList<>();
        return list;
    }
}
