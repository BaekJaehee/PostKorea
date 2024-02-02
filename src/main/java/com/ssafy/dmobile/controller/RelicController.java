package com.ssafy.dmobile.controller;

import com.ssafy.dmobile.entity.DetailData;
import com.ssafy.dmobile.entity.ListData;
import com.ssafy.dmobile.repository.DetailDataRepository;
import com.ssafy.dmobile.repository.ListDataRepository;
import com.ssafy.dmobile.repository.TagRelicRepository;
import com.ssafy.dmobile.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 이건 json이랑 xml 받을때
// rest api로 통신
@RequestMapping("/relic")
public class RelicController {
    @Autowired
    private ListDataRepository listDataRepository;

    @Autowired
    private DetailDataRepository detailDataRepository;

    @Autowired
    private TagRelicRepository tagRelicRepository;

    @Autowired
    private TagRepository tagRepository;


    @GetMapping("/list")
    public ResponseEntity<List<ListData>> getRelic() {
        List<ListData> listData = listDataRepository.findAll();
        return ResponseEntity.ok().body(listData);
    }


//    @PostMapping("/list")
//    public ResponseEntity<List<ListData>> getRelicWithTag(@RequestBody List<String> tag) {
//        List<ListData> listData = listDataRepository.findByTag(tag);
//        return ResponseEntity.ok().body(listData);
//    }

//    @PostMapping("/list")   // 태그로 리스트 검색
//    public ResponseEntity<List<ListData>> getRelicWithTag(@RequestBody List<String> tagName) {
//        List<Tag> tag = tagRepository.findByName(tagName);  // 입력해서 태그 가져오기
//        List<ListData> listData = listDataRepository.findByTagName(tag);    // 태그로 찾아서 반환하기
//        return ResponseEntity.ok().body(listData);
//    }

    @PostMapping("/list")   // 태그로 리스트 검색
    public ResponseEntity<List<DetailData>> getRelicWithTag(@RequestBody List<String> tags) {
        try {
            List<DetailData> result = tagRelicRepository.findByTag(tags);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            // 에러 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//        List<String> tagNames = tagData.getTagNames();
//        String ccceNameTag = tagData.getCcceNameTag();
//        String ccbaLcadTag = tagData.getCcbaLcadTag();
//        String mcodeNameTag = tagData.getMcodeNameTag();
//        String scodeNameTag = tagData.getScodeNameTag();

//        List<DetailData> detailDataList = detailDataRepository.findByTagIn(tagNames); // 해당하는 detaildata 리스트로 가져오기
//
//        if (detailDataList.isEmpty()) {
//            // 해당하는 DetailData가 없으면 에러
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }

//        if (tagNames != null && !tagNames.isEmpty()) {
//            // 태그가 2개 이상일 때 검색
//            detailDataList = tagRelicRepository.findListDataByTagNames(tagNames, (long) tagNames.size());
//        } else if (ccbaLcadTag != null && !ccbaLcadTag.isEmpty()) {
//            // 'ccbaLcadTag'만 있을 경우
//            listData = tagRelicRepository.findByListDataCcbaLcadTag(ccbaLcadTag);
//        } else if (ccceNameTag != null && !ccceNameTag.isEmpty()) {
//            // 'ccceNameTag'만 있을 경우
//            listData = tagRelicRepository.findByListDataCcceNameTag(ccceNameTag);
//        } else if (mcodeNameTag != null && !mcodeNameTag.isEmpty()) {
//            // mcodeNameTag
//            listData = tagRelicRepository.findByListDataMcodeNameTag(mcodeNameTag);
//        } else if (scodeNameTag != null && !scodeNameTag.isEmpty()) {
//            // scodeNameTag
//            listData = tagRelicRepository.findByListDataScodeNameTag(scodeNameTag);
//        } else {
//            // 태그가 없을 때 기본 검색
//            listData = tagRelicRepository.findAllListData();
//        }

//        return ResponseEntity.ok().body(detailDataList);

//        List<ListDetailDTO> response = new ArrayList<>();
//        for (DetailData detailData : detailDataList) {
//            ListData listData = detailData.getListData();
//            response.add(new ListDetailDTO(listData, detailData));
//        }
//
//        return ResponseEntity.ok().body(response);

//        if (tagNames != null && !tagNames.isEmpty()) {
//            // 태그가 2개 이상일 때 검색
//            listData = tagRelicRepository.findListDataByTagNames(tagNames, (long) tagNames.size());
//        } else if (ccbaLcadTag != null && !ccbaLcadTag.isEmpty()) {
//            // 'ccbaLcadTag'만 있을 경우
//            listData = tagRelicRepository.findByListDataCcbaLcadTag(ccbaLcadTag);
//        } else if (ccceNameTag != null && !ccceNameTag.isEmpty()) {
//            // 'ccceNameTag'만 있을 경우
//            listData = tagRelicRepository.findByListDataCcceNameTag(ccceNameTag);
//        } else if (mcodeNameTag != null && !mcodeNameTag.isEmpty()) {
//            // mcodeNameTag
//            listData = tagRelicRepository.findByListDataMcodeNameTag(mcodeNameTag);
//        } else if (scodeNameTag != null && !scodeNameTag.isEmpty()) {
//            // scodeNameTag
//            listData = tagRelicRepository.findByListDataScodeNameTag(scodeNameTag);
//        } else {
//            // 태그가 없을 때 기본 검색
//            listData = tagRelicRepository.findAllListData();
//        }
//
//        return ResponseEntity.ok().body(listData);
//    }

//    @PostMapping("/list")
//    public ResponseEntity<List<ListData>> getRelicWithTag(@RequestBody List<String> tag) {
//        List<ListData> listData = tagRelicRepository.findByTag(tag)
//                .stream()
//                .map(TagRelic::getListData)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok().body(listData);
//    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<DetailData> getDetail(@PathVariable Integer id) {
        DetailData detailData = detailDataRepository.findByListId(id);
        return ResponseEntity.ok().body(detailData);
    }
}
