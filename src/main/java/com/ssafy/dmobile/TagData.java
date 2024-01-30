package com.ssafy.dmobile;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TagData {
    private List<String> tagNames;  // 이걸 tagNames: [tag1, tag2 ...] 처럼
    private String ccceNameTag; // 시대
    private String ccbaLcadTag; // 소재지
    private String mcodeNameTag;    // 분류
    private String scodeNameTag;    // 분류
}
