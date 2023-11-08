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
    private String lifeCycle;
    @ApiModelProperty(notes = "가구상황")
    private String familySituation;

    public static UserEntity toEntity(final UserDTO dto) {
        UserDTO parseredDTO = parserFamilySituation(dto);
        UserEntity entity = new UserEntity();
        entity.setUsername(parseredDTO.getUsername());
        entity.setEmail(parseredDTO.getEmail());
        entity.setPassword(parseredDTO.getPassword());
        entity.setCtpvNm(parseredDTO.getCtpvNm());
        entity.setSggNm(parseredDTO.getSggNm());
        entity.setBirth(parseredDTO.getBirth());
        entity.setFamilySituation(FamilySituationEnum.valueOf(parseredDTO.getFamilySituation()));

        if(parseredDTO.getLifeCycle() == null) {
            entity.setLifeCycle(null);
        } else {
            entity.setLifeCycle(LifeCycleEnum.valueOf(parseredDTO.getLifeCycle()));
        }

        return entity;
    }

    public static UserDTO parserFamilySituation(final UserDTO dto) {
        if(dto.getFamilySituation().equals("한부모·조손")) {
            dto.setFamilySituation("한부모조손");
        }
        if(dto.getFamilySituation().equals("다문화·탈북민")) {
            dto.setFamilySituation("다문화탈북민");
        }

        return dto;
    }
}
