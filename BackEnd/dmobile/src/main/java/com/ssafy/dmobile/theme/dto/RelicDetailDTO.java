package com.ssafy.dmobile.theme.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RelicDetailDTO {
    private Long relicId;
    private Long itemId;
    private String ccmaName;
    private String ccbaMnm1;
    private String ccbaMnm2;
    private String ccbaKdcd;
    private String ccbaCtcd;
    private String ccbaAsno;
    private String ccbaCpno;
    private String longitude;
    private String latitude;
    private String gcodeName;
    private String bcodeName;
    private String mcodeName;
    private String scodeName;
    private String ccbaQuan;
    private String ccbaAsdt;
    private String ccbaLcad;
    private String ccceName;
    private String ccbaPoss;
    private String ccbaAdmin;
    private String imageUrl;
    private String content;
    private String region1;
    private String region2;

//    public RelicDetailDTO(Long relicId, Long itemId, String ccmaName, String ccbaMnm1, String ccbaMnm2, String ccbaKdcd, String ccbaCtcd, String ccbaAsno, String ccbaCpno, String longitude, String latitude, String gcodeName, String bcodeName, String mcodeName, String scodeName, String ccbaQuan, String ccbaAsdt, String ccbaLcad, String ccceName, String ccbaPoss, String ccbaAdmin, String imageUrl, String content, String region1, String region2) {
//    }
}

