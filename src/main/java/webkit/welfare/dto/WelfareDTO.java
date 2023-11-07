package webkit.welfare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WelfareDTO {
    private Date birth; //생년월일 -> 이용해서 생애주기 계산
    private String trgterIndvdlArray; //가구상황
    private String ctpvNm; //시도명
    private String sggNm; //시군구명
    private String srchKeyCode; //검색분류
    private String searchWrd; //검색어
    private String arrgOrd; //정렬순서
}
