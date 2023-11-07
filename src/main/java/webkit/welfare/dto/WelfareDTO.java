package webkit.welfare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WelfareDTO {
    private String lifeArray; //생애주기
    private String trgterIndvdlArray; //가구상황
    private String intrsThemaArray; //관심주제
    private String age; //나이
    private String ctpvNm; //시도명
    private String sggNm; //시군구명
    private String srchKeyCode; //검색분류
    private String searchWrd; //검색어
    private String arrgOrd; //정렬순서
}
