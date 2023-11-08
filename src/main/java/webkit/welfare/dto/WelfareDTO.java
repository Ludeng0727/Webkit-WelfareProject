package webkit.welfare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webkit.welfare.domain.FamilySituationEnum;
import webkit.welfare.domain.LifeCycleEnum;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WelfareDTO {
    private LifeCycleEnum lifeCycle; //생애주기
    private FamilySituationEnum familySituation; //가구상황
    private String ctpvNm; //시도명
    private String sggNm; //시군구명
}
