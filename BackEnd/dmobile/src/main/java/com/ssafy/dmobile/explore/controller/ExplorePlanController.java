package com.ssafy.dmobile.explore.controller;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/explore-plans")
@RequiredArgsConstructor
@Tag(name = "Explore Plan", description = "탐방 계획 API Document")
public class ExplorePlanController {

    private final ExplorePlanService explorePlanService;
    private final RelicExplorePlanService relicExplorePlanService;
    private final AuthTokensGenerator authTokensGenerator;

    @PostMapping
    @Operation(summary = "탐방 계획 생성", description = "이름, 시간 등의 정보를 받아 탐방 계획을 생성합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> createPlan(
            @RequestHeader("Authorization") String token,
            @RequestBody ExplorePlanDto explorePlanDto) {

        Long memberId = authTokensGenerator.extractMemberId(token);
        // Plan 생성
        ExplorePlan newPlan = explorePlanService.createPlan(explorePlanDto);
        // Plan 에 memberId 매핑
        explorePlanService.connectMemberToExplorePlan(memberId, newPlan.getPlanId());

        return ResponseEntity.ok(newPlan);
    }

    @GetMapping("/list/{planId}")
    @Operation(summary = "탐방 상세 내역 조회", description = "PlanId를 지닌 탐방 내 지금까지 다녀온 문화재 조회<br>DetailData 엔티티 그대로 출력되며 필요에 의해 DTO 생성 가능")
    @SecurityRequirement(name = "Authorization")
    public List<DetailData> getVisitedRelicList(
            @RequestHeader("Authorization") String token,
            @PathVariable Long planId) {

        Long memberId = authTokensGenerator.extractMemberId(token);
        // 접근 가능한 유저인지 확인
        if (!explorePlanService.canAccessPlan(memberId, planId)) {
            throw new CustomException(ExceptionType.INVALID_MEMBER_FOR_PLAN_EXCEPTION);
        }

        return relicExplorePlanService.getRelicsInPlan(planId);
    }

    // TODO: 탐방 계획 내에 사용자 매핑
    @GetMapping("/join/{planId}")
    @Operation(summary = "탐방 가입", description = "회원의 아이디를 받아 해당 회원을 탐방에 가입시캅니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> registerMemberInPlan(
            @RequestHeader("Authorization") String token,
            @PathVariable Long planId) {

        Long memberId = authTokensGenerator.extractMemberId(token);
        explorePlanService.connectMemberToExplorePlan(memberId, planId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{planId}")
    @Operation(summary = "탐방 계획 수정", description = "이름, 시간 등의 정보를 받아 탐방 계획을 수정합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updatePlanInfo(
            @RequestHeader("Authorization") String token,
            @PathVariable Long planId,
            @RequestBody ExplorePlanDto explorePlanDto) {

        Long memberId = authTokensGenerator.extractMemberId(token);
        // 접근 가능한 유저인지 확인
        if (!explorePlanService.canAccessPlan(memberId, planId)) {
            throw new CustomException(ExceptionType.INVALID_MEMBER_FOR_PLAN_EXCEPTION);
        }
        // 계획 정보 업데이트
        ExplorePlan updatedPlan = explorePlanService.updatePlan(planId, explorePlanDto);
        return ResponseEntity.ok().body(updatedPlan);
    }

    @DeleteMapping("/{planId}")
    @Operation(summary = "탐방 계획 삭제", description = "하당 아이디를 지닌 계획을 삭제합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> deletePlan(
            @RequestHeader("Authorization") String token,
            @PathVariable Long planId) {

        Long memberId = authTokensGenerator.extractMemberId(token);
        // 접근 가능한 유저인지 확인
        if (!explorePlanService.canAccessPlan(memberId, planId)) {
            throw new CustomException(ExceptionType.INVALID_MEMBER_FOR_PLAN_EXCEPTION);
        }

        explorePlanService.deletePlan(planId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ongoing")
    @Operation(summary = "진행중인 탐방", description = "현재 시간을 기준으로 진행중인 탐방을 출력합니다.")
    @SecurityRequirement(name = "Authorization")
    public List<ExplorePlan> getOngoingPlans(@RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        return explorePlanService.getOngoingPlans(memberId);
    }

    @GetMapping("/upcoming")
    @Operation(summary = "앞으로의 탐방", description = "현재 시간을 기준으로 계획중인 탐방을 출력합니다.")
    @SecurityRequirement(name = "Authorization")
    public List<ExplorePlan> getUpcomingPlans(@RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        return explorePlanService.getUpcomingPlans(memberId);
    }

    @GetMapping("/completed")
    @Operation(summary = "지금까지의 탐방", description = "현재 시간을 기준으로 지금까지 경험한 탐방을 출력합니다.")
    @SecurityRequirement(name = "Authorization")
    public List<ExplorePlan> getCompletedPlans(@RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        return explorePlanService.getCompletedPlans(memberId);
    }
}
