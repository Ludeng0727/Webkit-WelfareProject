package webkit.welfare.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webkit.welfare.domain.FamilySituationEnum;
import webkit.welfare.domain.LifeCycleEnum;
import webkit.welfare.domain.UserEntity;

import java.util.Date;

@ApiModel(description = "유저 정보 도메인")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @ApiModelProperty(notes = "JWT 토큰")
    private String token;
    @ApiModelProperty(notes = "식별자")
    private String id;
    @ApiModelProperty(notes = "이름")
    private String username;
    @ApiModelProperty(notes = "이메일")
    private String email;
    @ApiModelProperty(notes = "비밀번호")
    private String password;
    @ApiModelProperty(notes = "시도명")
    private String ctpvNm;
    @ApiModelProperty(notes = "시군구명")
    private String sggNm;
    @ApiModelProperty(notes = "생년월일")
    private Date birth;
    @ApiModelProperty(notes = "생애주기")
    private LifeCycleEnum lifeCycle;
    @ApiModelProperty(notes = "가구상황")
    private FamilySituationEnum familySituation;

    public static UserEntity toEntity(final UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setCtpvNm(dto.getCtpvNm());
        entity.setSggNm(dto.getSggNm());
        entity.setBirth(dto.getBirth());
        entity.setLifeCycle(dto.getLifeCycle());
        entity.setFamilySituation(dto.getFamilySituation());

        return entity;
    }
}
