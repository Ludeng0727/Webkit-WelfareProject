package webkit.welfare.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webkit.welfare.dto.WelfareDTO;
import webkit.welfare.service.WelfareService;

@Slf4j
@RestController
@RequestMapping("/welfare")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WelfareController {

    private final WelfareService welfareService;


//    @GetMapping("all")
//    public ResponseEntity<?> getAllWelfare(@ModelAttribute WelfareDTO welfareDTO){
//
//    }



}
