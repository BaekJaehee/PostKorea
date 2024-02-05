package com.ssafy.dmobile.explore.controller;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.explore.service.ExplorePlanService;
import com.ssafy.dmobile.explore.service.RelicExplorePlanService;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("explore-plans/manage")
@RequiredArgsConstructor
@Tag(name = "Explore Plan Management", description = "탐방 계획 내 문화재 관리 API Document")
public class ExplorePlanManageController {

    private final ExplorePlanService explorePlanService;
    private final RelicExplorePlanService relicExplorePlanService;
    private final AuthTokensGenerator authTokensGenerator;

    @PostMapping("/{planId}/{relicId}")
    @Operation(summary = "탐방 계획에 문화재 추가", description = "탐방의 id값과 문화재의 id값을 받아서 token의 id를 지닌 유저에게 추가합니다." +
            "<br>기본 방문여부는 False 이며 계획 추가 단계에서 T/F 여부는 받지 않습니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> addExplorePlan(
            @PathVariable Long relicId,
            @PathVariable Long planId,
            @RequestHeader("Authorization") String token) {

        Long memberId = authTokensGenerator.extractMemberId(token);

        if (!explorePlanService.canAccessPlan(planId, memberId)) {
            throw new CustomException(ExceptionType.INVALID_MEMBER_FOR_PLAN_EXCEPTION);
        }

        relicExplorePlanService.addRelicInPlan(planId, relicId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{planId}/bulk-add")
    @Operation(summary = "탐방 계획에 여러 문화재 추가", description = "탐방의 id값과 문화재의 id값들을 받아서 token의 id를 지닌 유저에게 추가합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> addMultipleRelicsToPlan(
            @PathVariable Long planId,
            @RequestBody List<Long> relicIds,
            @RequestHeader("Authorization") String token) {

        Long memberId = authTokensGenerator.extractMemberId(token);

        if (!explorePlanService.canAccessPlan(planId, memberId)) {
            throw new CustomException(ExceptionType.INVALID_MEMBER_FOR_PLAN_EXCEPTION);
        }

        relicExplorePlanService.addRelicListInPlan(planId, relicIds);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{planId}/{relicId}")
    @Operation(summary = "탐방 계획에 문화재 방문처리", description = "탐방의 id값과 문화재의 id값을 받아서 token의 id를 지닌 유저의 방문여부를 변경합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updateExplorePlan(
            @PathVariable Long relicId,
            @PathVariable Long planId,
            @RequestParam boolean visited,
            @RequestHeader("Authorization") String token) {

        Long memberId = authTokensGenerator.extractMemberId(token);

        if (!explorePlanService.canAccessPlan(planId, memberId)) {
            throw new CustomException(ExceptionType.INVALID_MEMBER_FOR_PLAN_EXCEPTION);
        }

        relicExplorePlanService.updateRelicInPlan(planId, relicId, visited);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{planId}/{relicId}")
    @Operation(summary = "탐방 계획에 문화재 삭제", description = "탐방의 id값과 문화재의 id값을 받아서 token의 id를 지닌 유저에게서 삭제합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> deleteExplorePlan(
            @PathVariable Long relicId,
            @PathVariable Long planId,
            @RequestHeader("Authorization") String token) {

        Long memberId = authTokensGenerator.extractMemberId(token);

        if (!explorePlanService.canAccessPlan(planId, memberId)) {
            throw new CustomException(ExceptionType.INVALID_MEMBER_FOR_PLAN_EXCEPTION);
        }

        relicExplorePlanService.deleteRelicInPlan(planId, relicId);
        return ResponseEntity.ok().build();
    }
}
