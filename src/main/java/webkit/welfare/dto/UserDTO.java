package webkit.welfare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webkit.welfare.domain.FamilySituationEnum;
import webkit.welfare.domain.LifeCycleEnum;
import webkit.welfare.domain.UserEntity;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String token;
    private String id;
    private String username;
    private String email;
    private String password;
    private String ctpvNm;
    private String sggNm;
    private Date birth;
    private String lifeCycle;
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
