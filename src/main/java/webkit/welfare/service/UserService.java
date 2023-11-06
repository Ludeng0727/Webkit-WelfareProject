package webkit.welfare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webkit.welfare.domain.UserEntity;
import webkit.welfare.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //회원 정보 생성
    public UserEntity addUser(UserEntity userEntity) {
        // 회원 정보 누락 검사
        if(!validate(userEntity)) {
            throw new RuntimeException("Invalid arguments.");
        }
        final String email = userEntity.getEmail();

        // 이메일 중복 검사
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists.");
        }

        return userRepository.save(userEntity);
    }

    // 회원 정보 수정
    public UserEntity updateUser(UserEntity userEntity) {

        // 회원 정보 존재 여부 확인
        if(!userRepository.existsById(userEntity.getId())) {
            throw new RuntimeException("Unknown ID");
        }
        
        // 회원 정보 갱신
        userRepository.save(userEntity);

        // 갱신된 회원 정보 반환
        return userRepository.findById(userEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found."));
    }

    // 회원 정보 유효성 확인(Email, Password)
    public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
        final UserEntity originalUser = userRepository.findByEmail(email);
        if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }

    // 회원 정보 유효성 확인(id)
    public UserEntity findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found."));
    }

    // 누락 항목 검사
    public Boolean validate(UserEntity userEntity) {
        if(userEntity.getEmail() == null || userEntity.getPassword() == null || userEntity.getCtpvNm() == null || userEntity.getSggNm() == null) {
            return false;
        }
        return true;
    }
}
