package webkit.welfare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import webkit.welfare.domain.UserEntity;
import webkit.welfare.dto.ResponseDTO;
import webkit.welfare.dto.UserDTO;
import webkit.welfare.security.TokenProvider;
import webkit.welfare.service.UserService;

@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;
    
    final private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // JWT 토큰을 사용한 회원 정보 검색
    @GetMapping("/getUser")
    public ResponseEntity<?> retrieveUser(@AuthenticationPrincipal String userId) {
        try{
            UserEntity user = userService.findById(userId);
            return ResponseEntity.ok().body(user);
        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 회원 정보 수정(비밀번호 변경)
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal String userId, @RequestBody UserDTO userDTO) {
        try{
            // 유저 정보 검색 및 비밀번호 변경
            // 비밀번호에 암호화 과정이 존재하기 때문에 비밀번호 변경만 따로 제작
            UserEntity user = userService.findById(userId);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userService.updateUser(user);
            
            return ResponseEntity.ok().body(userService.findById(userId));
        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    
    // 회원 정보 수정(비밀번호 외)
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal String userId, @RequestBody UserDTO userDTO) {
        try{
            // 유저 정보 검색 및 회원 정보 변경
            UserEntity user = userService.findById(userId);
            user.setUsername(userDTO.getUsername());
            userDTO.setCtpvNm(userDTO.getCtpvNm());
            userDTO.setSggNm(userDTO.getSggNm());

            userService.updateUser(user);

            return ResponseEntity.ok().body(userService.findById(userId));
        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try{
            // 받아온 DTO로 UserEntity 생성
            UserEntity user = UserEntity.builder()
                    .username(userDTO.getUsername())
                    .email(userDTO.getEmail())
                    .password(userDTO.getPassword())
                    .ctpvNm(userDTO.getCtpvNm())
                    .sggNm(userDTO.getSggNm())
                    .build();

            // DB에 저장 후 해당 회원 정보 가져오기
            UserEntity registerUser = userService.addUser(user);

            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registerUser.getId())
                    .username(registerUser.getUsername())
                    .email(registerUser.getEmail())
                    .password(registerUser.getPassword())
                    .ctpvNm(registerUser.getCtpvNm())
                    .sggNm(registerUser.getSggNm())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword(), passwordEncoder);

        if(user != null) {
            // 로그인 성공 처리
            // JWT 토큰 생성 후 토큰과 함께 회원 정보 일부를 같이 반환
            final String token = tokenProvider.create(user);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .token(token)
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);

        } else {
            // 로그인 실패 처리
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error("Login failed").build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
