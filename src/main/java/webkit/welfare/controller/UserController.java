package webkit.welfare.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import webkit.welfare.domain.UserEntity;
import webkit.welfare.dto.AddUserRequest;
import webkit.welfare.dto.LoginUserDTO;
import webkit.welfare.dto.ResponseDTO;
import webkit.welfare.dto.UserDTO;
import webkit.welfare.security.TokenProvider;
import webkit.welfare.service.UserService;

import javax.validation.Valid;

@Api(tags = {"유저 정보 관리"})
@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;
    
    final private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // JWT 토큰을 사용한 회원 정보 검색
    @ApiOperation(value = "회원 정보 가져오기", notes = "JWT 토큰을 사용하여 회원 정보를 가져옵니다.")
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
    @ApiOperation(value = "비밀번호 변경", notes = "로그인한 회원의 비밀번호를 변경합니다.")
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal String userId, @RequestBody LoginUserDTO userDTO) {
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
    @ApiOperation(value = "회원 정보 변경", notes = "비밀번호를 제외한 회원 정보를 변경합니다.")
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal String userId, @RequestBody UserDTO userDTO) {
        try{
            UserEntity user = UserDTO.toEntity(userDTO);

            // 유저 정보 검색 및 회원 정보 변경
            UserEntity originalUser = userService.findById(userId);
            originalUser.setUsername(userDTO.getUsername());
            originalUser.setCtpvNm(userDTO.getCtpvNm());
            originalUser.setSggNm(userDTO.getSggNm());
            originalUser.setBirth(userDTO.getBirth());
            originalUser.setFamilySituation(user.getFamilySituation());
            originalUser.setLifeCycle(user.getLifeCycle());

            userService.updateUser(originalUser);

            return ResponseEntity.ok().body(userService.findById(userId));
        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 회원가입
    @ApiOperation(value = "회원 가입", notes = "회원 가입을 진행합니다.")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid AddUserRequest userDTO) {
        try{
            UserDTO dto = AddUserRequest.toUserDTO(userDTO);
            UserEntity user = UserDTO.toEntity(dto);
            System.out.println(user);
            // 받아온 DTO로 UserEntity 생성
            UserEntity newUser = UserEntity.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .ctpvNm(user.getCtpvNm())
                    .sggNm(user.getSggNm())
                    .birth(userDTO.getBirth())
                    .lifeCycle(user.getLifeCycle())
                    .familySituation(user.getFamilySituation())
                    .build();

            // DB에 저장 후 해당 회원 정보 가져오기
            UserEntity registerUser = userService.addUser(newUser);

            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registerUser.getId())
                    .username(registerUser.getUsername())
                    .email(registerUser.getEmail())
                    .password(registerUser.getPassword())
                    .ctpvNm(registerUser.getCtpvNm())
                    .sggNm(registerUser.getSggNm())
                    .birth(registerUser.getBirth())
                    .lifeCycle(registerUser.getLifeCycle())
                    .familySituation(registerUser.getFamilySituation())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 로그인
    @ApiOperation(value = "로그인", notes = "로그인 성공 시 JWT 토큰을 발행합니다.")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDTO userDTO) {
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
