package com.ssafy.dmobile.relic.controller;

import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.entity.ListData;
import com.ssafy.dmobile.relic.mapping.RegionMapping;
import com.ssafy.dmobile.relic.mapping.RegionMappings;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
import com.ssafy.dmobile.relic.repository.ListDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

//import static com.sun.beans.introspect.PropertyInfo.Name.required;

@RestController // 이건 json이랑 xml 받을때
// rest api로 통신
@RequestMapping("/relic")
@RequiredArgsConstructor
public class RelicController {

    private final ListDataRepository listDataRepository;
    private final DetailDataRepository detailDataRepository;

    @GetMapping("/list")
    public ResponseEntity<?> getRelic() {
        int limit = 10; // 개수 임의 설정
        Pageable pageable = PageRequest.of(0, limit);
        List<DetailData> detailData = detailDataRepository.findDataByLimit(pageable);
        return ResponseEntity.ok().body(detailData);
    }
    // 조인 해보려다가 주말안에 될지를 모르겠어서 일단 DetailData에서 이미지 주소랑 이름만

//    @GetMapping("/list")
//    public ResponseEntity<List<ListData>> getRelic() {
//        int limit = 50; // 리미트 개수 임의 설정
//        Pageable pageable = PageRequest.of(0, limit);
//        List<ListData> listData = listDataRepository.findListDataByLimit(pageable);
//        return ResponseEntity.ok().body(listData);
//    }

    @GetMapping("/search")
    public ResponseEntity<List<DetailData>> searchData(
            @RequestParam(required = false) String region1,
            @RequestParam(required = false) String region2,
            @RequestParam(required = false) String ccceName,
            @RequestParam(required = false) String scodeName) {
        // RegionMappings를 활용하여 입력된 지역 정보와 일치하는 지역명으로 변환
        String mappedRegion1 = mapRegion(region1);
        String mappedRegion2 = mapRegion(region2);

        List<DetailData> result = detailDataRepository.findbyTags(
                mappedRegion1, mappedRegion2, ccceName, scodeName
        );

        return ResponseEntity.ok().body(result);
    }
    private String mapRegion(String inputRegion) {
        for (RegionMapping regionMapping : RegionMappings.regionMappings) {
            if (regionMapping.getDbValue().equals(inputRegion)) {
                return regionMapping.getDbValue();
            }
        }
        return inputRegion; // 일치하는 값이 없으면 그대로 반환(기타 전국연합 같은거)
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<DetailData> getDetail(@PathVariable Long id) {
        DetailData detailData = detailDataRepository.findByItemId(id);
        return ResponseEntity.ok().body(detailData);
    }
}
