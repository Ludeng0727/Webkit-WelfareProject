package webkit.welfare.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


// 회원 가입 시 입력한 항목들의 제약사항을 확인하기 위해 사용
@Getter
@RequiredArgsConstructor
public class AddUserRequest {
    @Email
    private final String email;

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    @NotBlank
    private final String ctpvNm;

    @NotBlank
    private final String sggNm;
}
