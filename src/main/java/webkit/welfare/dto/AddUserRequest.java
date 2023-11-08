package webkit.welfare.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


// 회원 가입 시 입력한 항목들의 제약사항을 확인하기 위해 사용
@Data
@Builder
@AllArgsConstructor
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

    @NotNull
    private final Date birth;

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
