package webkit.welfare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webkit.welfare.domain.LifeCycleEnum;
import webkit.welfare.domain.UserEntity;
import webkit.welfare.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Date;

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

        // 생년월일로 생애주기 할당
        userEntity.setLifeCycle(AgeCalculate(userEntity));

        return userRepository.save(userEntity);
    }

    // 회원 정보 수정
    public UserEntity updateUser(UserEntity userEntity) {

        // 회원 정보 존재 여부 확인
        if(!userRepository.existsById(userEntity.getId())) {
            throw new RuntimeException("Unknown ID");
        }

        // 생년월일로 생애 주기 할당
        userEntity.setLifeCycle(AgeCalculate(userEntity));

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
        if(userEntity.getEmail() == null || userEntity.getPassword() == null
                || userEntity.getCtpvNm() == null || userEntity.getSggNm() == null
                || userEntity.getBirth() == null || userEntity.getFamilySituation() == null) {
            return false;
        }
        return true;
    }

    public LifeCycleEnum AgeCalculate(UserEntity userEntity) {
        // 생년월일을 사용하여 만 나이 계산
        Date currentDate = new Date();
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(userEntity.getBirth());

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentDate);

        int age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
        if(currentCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        // 계산된 만 나이로 생애 주기 판단
        if(age>=6 && age<=12) {
            return LifeCycleEnum.CHILD;
        } else if(age>=13 && age<=18) {
            return LifeCycleEnum.TEEN;
        } else if(age>=19 && age<=29) {
            return LifeCycleEnum.YOUTH;
        } else if(age>=30 && age<=64) {
            return LifeCycleEnum.MIDDLE_AGE;
        } else if(age>=65) {
            return LifeCycleEnum.OLD_AGE;
        }

        return null;
    }
}
