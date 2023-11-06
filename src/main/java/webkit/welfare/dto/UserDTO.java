package webkit.welfare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webkit.welfare.domain.UserEntity;

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
    private List<String> bookmarks;

    public static UserEntity toEntity(final UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setCtpvNm(dto.getCtpvNm());
        entity.setSggNm(dto.getSggNm());

        return entity;
    }
}
