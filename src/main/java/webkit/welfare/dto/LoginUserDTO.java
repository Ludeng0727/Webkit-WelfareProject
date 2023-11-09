package webkit.welfare.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "로그인 및 비밀번호 변경용 도메인")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDTO {
    @ApiModelProperty(notes = "이메일")
    private String email;
    @ApiModelProperty(notes = "비밀번호")
    private String password;
}
