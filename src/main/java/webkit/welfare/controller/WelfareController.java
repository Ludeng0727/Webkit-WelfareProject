package webkit.welfare.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin(origins = "*")
public class WelfareController {

    private final WelfareService welfareService;


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
