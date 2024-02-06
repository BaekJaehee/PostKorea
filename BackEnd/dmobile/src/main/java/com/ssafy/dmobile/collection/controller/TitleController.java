package com.ssafy.dmobile.collection.controller;

import com.ssafy.dmobile.collection.entity.title.Title;
import com.ssafy.dmobile.collection.service.TitleMemberService;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/title")
public class TitleController {

    private final AuthTokensGenerator authTokensGenerator;
    private final TitleMemberService titleMemberService;

    @GetMapping("/list")
    @Operation(summary = "획득 칭호 조회", description = "사용자의 Token을 받아 해당 사용자가 획득한 칭호 리스트를 조회합니다.")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> getAchieveList(@RequestHeader("Authorization") String token) {

        Long memberId = authTokensGenerator.extractMemberId(token);
        List<Title> titleList = titleMemberService.findTitleList(memberId);

        return ResponseEntity.ok(titleList);
    }

    //
    @GetMapping("/info/{titleId}")
    @Operation(summary = "칭호 획득", description = "사용자의 Token을 받아 titleId를 지닌 칭호의 ")
    public ResponseEntity<?> getAchieveList(@PathVariable Long titleId) {

        Title info = titleMemberService.getTitleInfo(titleId);
        return ResponseEntity.ok(info);
    }
}
