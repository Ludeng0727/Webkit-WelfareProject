package webkit.welfare.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


// 회원 가입 시 입력한 항목들의 제약사항을 확인하기 위해 사용
@ApiModel(description = "회원가입용 유저 정보 도메인")
@Data
@Builder
@AllArgsConstructor
public class AddUserRequest {
    @ApiModelProperty(notes = "이메일")
    @Email
    private final String email;

    @ApiModelProperty(notes = "이름")
    @NotBlank
    private final String username;

    @ApiModelProperty(notes = "비밀번호")
    @NotBlank
    private final String password;

    @ApiModelProperty(notes = "시도명")
    @NotBlank
    private final String ctpvNm;

    @ApiModelProperty(notes = "시군구명")
    @NotBlank
    private final String sggNm;

    @ApiModelProperty(notes = "생년월일")
    @NotNull
    private final Date birth;

    @ApiModelProperty(notes = "가구상황")
    @NotBlank
    private final String familySituation;

    public static UserDTO toUserDTO(final AddUserRequest dto) {
        UserDTO userDTO = UserDTO.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .ctpvNm(dto.getCtpvNm())
                .sggNm(dto.getSggNm())
                .birth(dto.getBirth())
                .familySituation(dto.getFamilySituation())
                .build();

        return userDTO;
    }
}
