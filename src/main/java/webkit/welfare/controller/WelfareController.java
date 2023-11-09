package webkit.welfare.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import webkit.welfare.domain.WelfareEntity;
import webkit.welfare.dto.ResponseDTO;
import webkit.welfare.service.WelfareService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/welfare")
@RequiredArgsConstructor
@Api(tags = "Welfare")
public class WelfareController {

    private final WelfareService welfareService;


    @ApiOperation(value = "모든 복지서비스 조회")
    @GetMapping("/all")
    public ResponseEntity<?> getAllWelfare(){
        try{
            List<WelfareEntity> allWelfare = welfareService.getAllWelfare();
            return ResponseEntity.ok(allWelfare);

        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @ApiOperation(value = "사용자 정보를 이용한 맞춤 복지서비스 조회")
    @GetMapping("/user")
    public ResponseEntity<?> getAllWelfareByUserInfo(@AuthenticationPrincipal @ApiIgnore String userId){
        try{
            List<WelfareEntity> allWelfare = welfareService.getAllWelfareByUserInfo(userId);
            return ResponseEntity.ok(allWelfare);

        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


    @ApiOperation(value = "키워드를 포함한 복지서비스 이름 조회")
    @ApiImplicitParam(name = "keyword", value = "검색어", example = "저소득")
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> getAllWelfareByKeyword(@PathVariable String keyword){
        try{
            List<WelfareEntity> welfareList = welfareService.getAllWelfareByKeyword(keyword);
            return ResponseEntity.ok(welfareList);

        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
