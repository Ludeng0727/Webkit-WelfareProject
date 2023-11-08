package webkit.welfare.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webkit.welfare.domain.FamilySituationEnum;
import webkit.welfare.domain.LifeCycleEnum;
import webkit.welfare.domain.WelfareEntity;
import webkit.welfare.dto.WelfareDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WelfareServiceTest {

    @Autowired
    WelfareService welfareService;

    @Test
    void getAllWelfareByWelfareDTO() {
        WelfareDTO welfareDTO = new WelfareDTO(LifeCycleEnum.노년, FamilySituationEnum.저소득, "경상북도", "구미시");
        List<WelfareEntity> all = welfareService.getAllWelfareByWelfareDTO(welfareDTO);

        all.forEach(welfare->{
            System.out.println("=============");
            System.out.println(welfare.getServId());
            System.out.println(welfare.getLifeNmArray());
            System.out.println(welfare.getTrgterIndvdlNmArray());
            System.out.println(welfare.getCtpvNm());
            System.out.println(welfare.getSggNm());
            System.out.println("=============");
        });


    }
}