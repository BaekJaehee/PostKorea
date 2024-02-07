package com.ssafy.dmobile.relic.controller;

import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
import com.ssafy.dmobile.relic.service.DetailDataService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController // 이건 json이랑 xml 받을때
// rest api로 통신
@RequestMapping("/relic")
@RequiredArgsConstructor
public class RelicController {

    // RequiredArgsConstructor 쓸때는 final 필요
    private final DetailDataRepository detailDataRepository;
    private final DetailDataService detailDataService;

    @GetMapping("/list")
    @Operation(summary = "목록 보기", description = "전체 목록 보기")
    public ResponseEntity<?> getRelic() {
        int limit = 10;
        Pageable pageable = PageRequest.of(0, limit);
        List<DetailData> detailData = detailDataRepository.findDataByLimit(pageable);
        return ResponseEntity.ok().body(detailData);
    }

    @GetMapping("/find")    // 포함되는 이름이 있으면 반환
    @Operation(summary = "이름 검색", description = "이름으로 검색해서 포함되는 문자가 있는 행 반환")
    public ResponseEntity<List<DetailData>> findData(@RequestParam String name) {
        List<DetailData> result = detailDataRepository.findByName(name);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/search")
    @Operation(summary = "조건 검색", description = "시/도, 시/군/구, 시대, 분류를 파라미터로 검색해 해당하는 행 반환")
    public ResponseEntity<List<DetailData>> searchData(
            @RequestParam(required = false) String region1,
            @RequestParam(required = false) String region2,
            @RequestParam(required = false) String era,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        // 임의로 10개씩 나오게 설정

        String mappingRegion1 = detailDataService.mappingRegion(region1);

//        System.out.println(region1 + mappingRegion1 + region2 + ccceName + mcodeName);

        PageRequest pageRequest = PageRequest.of(page, limit);

        List<DetailData> result = detailDataRepository.findbyTags(
            region1, mappingRegion1, region2, era, category, pageRequest
        );
        // pageRequest 파라미터에 추가

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "상세보기", description = "문화재 이미지, 내용 등 상세보기")
    public ResponseEntity<DetailData> getDetail(@PathVariable Long id) {
        DetailData detailData = detailDataRepository.findByItemId(id);
        return ResponseEntity.ok().body(detailData);
    }

    @GetMapping("/random/{relicId}")
    @Operation(summary = "게임 실행 시 사진 랜덤 출력", description = "해당하는 태그의 문화재 리스트에서 임의의 개수만큼 사진 랜덤 출력")
    public List<String> printRandomly(@PathVariable Long relicId) {
        // 게임의 태그(카테고리)에 해당하는 문화재 리스트를 임의의 개수 n만큼 불러오고 거기서 또 임의의 개수 m(m < n)의 사진 출력
        // 사진은 매 게임마다 다르게 출력되어야 함

        // 해당 문화재 데이터 가져오기
        Optional<DetailData> detailData = detailDataService.findById(relicId);
        if (detailData.isPresent()) {
            DetailData dd = detailData.get();
            // mcode와 일치하면서 relicId가 다른 DetailData 데이터 가져오기
            List<DetailData> data = detailDataService.getDataByCategory(dd.getMcodeName(), relicId);

            // 선택된 Relic 데이터에서 imageUrl만 추출하여 리스트에 담기
//            List<String> selectedImageUrls = data.stream()
//                    .map(DetailData::getImageUrl)
//                    .collect(Collectors.toList());

            // 원래 문화재의 이미지 URL(정답)
            String selectedImageUrl = dd.getImageUrl();

            // 나머지 랜덤한 이미지 URL 가져오기
            List<String> randomImageUrls = getRandomImageUrls(data, selectedImageUrl);

            // 선택된 문화재의 이미지 URL을 먼저 리스트에 추가
            List<String> selectedImageUrls = new ArrayList<>();
            selectedImageUrls.add(selectedImageUrl);

            // 임의의 이미지 개수
            int images = 4;
            // 나머지 랜덤한 이미지 URL을 count만큼 추가하여 반환
            selectedImageUrls.addAll(randomImageUrls.subList(0, Math.min(randomImageUrls.size(), images)));
            return selectedImageUrls;
        } else {
            // 유물을 찾지 못한 경우
            // 는 없을건데 Optional<>때문에..
            return List.of();
        }
    }

    private List<String> getRandomImageUrls(List<DetailData> data, String selectedImageUrl) {
        List<String> imageUrls = data.stream()
                .map(DetailData::getImageUrl)
                .filter(url -> url != null && !url.equals(selectedImageUrl)) // null 체크 추가
                .collect(Collectors.toList());
        Collections.shuffle(imageUrls); // 이미지 URL 리스트를 랜덤하게 섞음
        return imageUrls;
    }

}
