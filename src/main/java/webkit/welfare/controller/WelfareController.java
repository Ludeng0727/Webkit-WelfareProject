package webkit.welfare.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webkit.welfare.domain.FamilySituationEnum;
import webkit.welfare.domain.LifeCycleEnum;
import webkit.welfare.domain.UserEntity;
import webkit.welfare.domain.WelfareEntity;
import webkit.welfare.dto.ResponseDTO;
import webkit.welfare.dto.WelfareDTO;
import webkit.welfare.service.WelfareService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/welfare")
@RequiredArgsConstructor
@Api(tags = "Welfare")
public class WelfareController {

    private final WelfareService welfareService;


    @ApiOperation(
            value = "사용자 정보를 이용한 맞춤 복지서비스 조회"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "ctpvNm",
                    value = "시도",
                    example = "경상북도"
            ),
            @ApiImplicitParam(
                    name = "familySituation",
                    value = "가구상황",
                    example = "LOW_INCOME"
            ),
            @ApiImplicitParam(
                    name = "lifeCycle",
                    value = "생애주기",
                    example = "OLD_AGE"
            ),
            @ApiImplicitParam(
                    name = "sggNm",
                    value = "시군구",
                    example = "구미시"
            )

    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllWelfare(@ModelAttribute WelfareDTO welfareDTO){
        try{
            List<WelfareEntity> allWelfare = welfareService.getAllWelfareByWelfareDTO(welfareDTO);
            return ResponseEntity.ok(allWelfare);

        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


    @ApiOperation(
            value = "키워드를 포함한 복지서비스 이름 조회"
    )
    @ApiImplicitParam(
            name = "keyword",
            value = "검색어",
            example = "저소득"
    )
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
