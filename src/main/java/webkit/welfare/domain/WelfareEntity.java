package webkit.welfare.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WelfareEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String servId;
    private String ctpvNm; //시도명
    private String sggNm; //시군구명
    private String servDgst; //서비스요약
    private String servDtlLink; //서비스 상세 링크
    private String lifeNmArray; //생애주기명
    private String intrsThemaNmArray;//관심주제명
    private String sprtCycNm; //지원주기명(ex: 월, 수시)
    private String srvPvsnNm; //제공유형명(ex: 현금지급, 감면)
    private String aplyMtdNm; //신청방법명(ex : 인터넷, 우편)
    private String inqNum; //조회수
    private String lastModYmd; //최종수정일자
    private String servNm; //서비스명
    private String trgterIndvdlNmArray; //가구상황명(ex: 한부모, 조손)

}
