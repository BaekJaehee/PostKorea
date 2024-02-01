package com.ssafy.dmobile.explore.controller;

import com.ssafy.dmobile.explore.entity.ExplorePlan;
import com.ssafy.dmobile.explore.service.ExplorePlanService;
import com.ssafy.dmobile.explore.service.RelicExplorePlanService;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("explore-plans")
@RequiredArgsConstructor
@Tag(name = "Explore Plan", description = "탐방 계획 API Document")
public class ExplorePlanController {

    private final ExplorePlanService explorePlanService;
    private final RelicExplorePlanService relicExplorePlanService;
    private final AuthTokensGenerator authTokensGenerator;

    // DTO를 사용하면 좋으나, 시간관계상 List 그대로 출력 (변환은 Jackson 라이브러리 제공)
    @GetMapping("/ongoing")
    @Operation(summary = "진행중인 탐방", description = "현재 시간을 기준으로 진행중인 탐방을 출력합니다.")
    @SecurityRequirement(name = "Authorization")
    public List<ExplorePlan> getOngoingPlans(@RequestHeader("Authorization") String token) {
        Long userId = authTokensGenerator.extractMemberId(token); // 토큰에서 userId 추출하는 로직 구현 필요
        return explorePlanService.getOngoingPlans(userId);
    }

    @GetMapping("/upcoming")
    @Operation(summary = "앞으로의 탐방", description = "현재 시간을 기준으로 계획중인 탐방을 출력합니다.")
    @SecurityRequirement(name = "Authorization")
    public List<ExplorePlan> getUpcomingPlans(@RequestHeader("Authorization") String token) {
        Long userId = authTokensGenerator.extractMemberId(token); // 토큰에서 userId 추출하는 로직 구현 필요
        return explorePlanService.getUpcomingPlans(userId);
    }

    @GetMapping("/completed")
    @Operation(summary = "지금까지의 탐방", description = "현재 시간을 기준으로 지금까지 경험한 탐방을 출력합니다.")
    @SecurityRequirement(name = "Authorization")
    public List<ExplorePlan> getCompletedPlans(@RequestHeader("Authorization") String token) {
        Long userId = authTokensGenerator.extractMemberId(token); // 토큰에서 userId 추출하는 로직 구현 필요
        return explorePlanService.getCompletedPlans(userId);
    }

    @PostMapping("/manage/{planId}/{relicId}")
    @Operation(summary = "탐방 계획에 문화재 추가", description = "탐방의 id값과 문화재의 id값을 받아서 token의 id를 지닌 유저에게 추가합니다. 기본 방문여부는 false이며 계획 추가 단계에서 t/f여부는 받지 않습니다.")
    @SecurityRequirement(name = "Authorization")
    public void addExplorePlan(
            @PathVariable Long relicId,
            @PathVariable Long planId,
            @RequestHeader("Authorization") String token) {
        Long userId = authTokensGenerator.extractMemberId(token); // 토큰에서 userId 추출하는 로직 구현 필요
        relicExplorePlanService.addRelicToPlan(planId, relicId, userId);
    }

    @PutMapping("/manage/{planId}/{relicId}")
    @Operation(summary = "탐방 계획에 문화재 방문처리", description = "탐방의 id값과 문화재의 id값을 받아서 token의 id를 지닌 유저의 방문여부를 변경합니다.")
    @SecurityRequirement(name = "Authorization")
    public void updateExplorePlan(
            @PathVariable Long relicId,
            @PathVariable Long planId,
            @RequestParam boolean visited,
            @RequestHeader("Authorization") String token) {
        Long userId = authTokensGenerator.extractMemberId(token); // 토큰에서 userId 추출하는 로직 구현 필요
        relicExplorePlanService.updateRelicToPlan(planId, relicId, userId, visited);
    }

    @DeleteMapping("/manage/{planId}/{relicId}")
    @Operation(summary = "탐방 계획에 문화재 삭제", description = "탐방의 id값과 문화재의 id값을 받아서 token의 id를 지닌 유저에게서 삭제합니다.")
    @SecurityRequirement(name = "Authorization")
    public void deleteExplorePlan(
            @PathVariable Long relicId,
            @PathVariable Long planId,
            @RequestHeader("Authorization") String token) {
        Long userId = authTokensGenerator.extractMemberId(token); // 토큰에서 userId 추출하는 로직 구현 필요
        relicExplorePlanService.deleteRelicToPlan(planId, relicId, userId);
    }
}
