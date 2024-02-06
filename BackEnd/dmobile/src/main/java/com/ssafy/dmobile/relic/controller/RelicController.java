package com.ssafy.dmobile.relic.controller;

import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
import com.ssafy.dmobile.relic.service.DetailDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 이건 json이랑 xml 받을때
// rest api로 통신
@RequestMapping("/relic")
@RequiredArgsConstructor
public class RelicController {

    // RequiredArgsConstructor 쓸때는 final 필요
    private final DetailDataRepository detailDataRepository;
    private final DetailDataService detailDataService;

    @GetMapping("/list")
    public ResponseEntity<?> getRelic() {
        int limit = 10;
        Pageable pageable = PageRequest.of(0, limit);
        List<DetailData> detailData = detailDataRepository.findDataByLimit(pageable);
        return ResponseEntity.ok().body(detailData);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DetailData>> searchData(
            @RequestParam(required = false) String region1,
            @RequestParam(required = false) String region2,
            @RequestParam(required = false) String ccceName,
            @RequestParam(required = false) String mcodeName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        // 임의로 10개씩 나오게 설정

        String mappingRegion1 = detailDataService.mappingRegion(region1);

//        System.out.println(region1 + mappingRegion1 + region2 + ccceName + mcodeName);

        PageRequest pageRequest = PageRequest.of(page, limit);

        List<DetailData> result = detailDataRepository.findbyTags(
            region1, mappingRegion1, region2, ccceName, mcodeName, pageRequest
        );
        // pageRequest 파라미터에 추가

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<DetailData> getDetail(@PathVariable Long id) {
        DetailData detailData = detailDataRepository.findByItemId(id);
        return ResponseEntity.ok().body(detailData);
    }
}
