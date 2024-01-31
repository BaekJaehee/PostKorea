package com.ssafy.dmobile.relic.controller;

import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.entity.ListData;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
import com.ssafy.dmobile.relic.repository.ListDataRepository;
//import com.ssafy.dmobile.service.DetailDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 이건 json이랑 xml 받을때
// rest api로 통신
@RequestMapping("/relic")
@Tag(name = "Relic", description = "문화재 관리 API Document")
public class RelicController {
    @Autowired
    private ListDataRepository listDataRepository;

    @Autowired
    private DetailDataRepository detailDataRepository;
    
    @GetMapping("/list")
    @Operation(summary = "문화재 리스트", description = "문화재 리스트를 출력합니다.")
    public ResponseEntity<List<ListData>> getRelic() {
        List<ListData> listData = listDataRepository.findAll();
        return ResponseEntity.ok().body(listData);
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "문화재 디테일", description = "문화재 상세를 출력합니다.")
    public ResponseEntity<DetailData> getDetail(@PathVariable Integer id) {
        DetailData detailData = detailDataRepository.findByListId(id);
        return ResponseEntity.ok().body(detailData);
    }
}
