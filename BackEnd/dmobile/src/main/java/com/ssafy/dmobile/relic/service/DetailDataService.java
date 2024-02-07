package com.ssafy.dmobile.relic.service;

import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DetailDataService {
    private final DetailDataRepository detailDataRepository;
    public String mappingRegion(String region) {
        if (region == null) {
            return null;
        }
        return switch (region) {
            case "경상북도" -> "경북";
            case "경북" -> "경상북도";
            case "경상남도" -> "경남";
            case "경남" -> "경상남도";
            case "전라북도" -> "전북";
            case "전북" -> "전라북도";
            case "전라남도" -> "전남";
            case "전남" -> "전라남도";
            case "충청북도" -> "충북";
            case "충북" -> "충청북도";
            case "충청남도" -> "충남";
            case "충남" -> "충청남도";
            case "경기도" -> "경기";
            case "경기" -> "경기도";
            case "강원도" -> "강원";
            case "강원" -> "강원도";
            case "서울특별시" -> "서울";
            case "서울" -> "서울특별시";
            case "인천광역시" -> "인천";
            case "인천" -> "인천광역시";
            case "대전광역시" -> "대전";
            case "대전" -> "대전광역시";
            case "대구광역시" -> "대구";
            case "대구" -> "대구광역시";
            case "부산광역시" -> "부산";
            case "부산" -> "부산광역시";
            case "광주광역시" -> "광주";
            case "광주" -> "광주광역시";
            case "울산광역시" -> "울산";
            case "울산" -> "울산광역시";
            case "제주특별자치도" -> "제주";
            case "제주" -> "제주특별자치도";
            case "세종특별자치시" -> "세종";
            case "세종" -> "세종특별자치시";

            default -> region;
        };
    }

    public Optional<DetailData> findById(Long relicId) {
        return detailDataRepository.findById(relicId);
    }

    public List<DetailData> getDataByCategory(String mcodeName, Long relicId) {
        return detailDataRepository.findByMcodeNameAndRelicIdNot(mcodeName, relicId);
//        return detailDataRepository.getDataByCategory(mcodeName, relicId);
    }
}
