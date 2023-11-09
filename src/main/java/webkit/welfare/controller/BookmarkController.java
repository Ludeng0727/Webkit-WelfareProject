package webkit.welfare.controller;

import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import webkit.welfare.domain.BookmarkEntity;
import webkit.welfare.dto.ResponseDTO;
import webkit.welfare.service.BookmarkService;

import java.util.List;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;


    @PostMapping("/register")
    @ApiImplicitParam(name = "authorization", value = "Bearer + {JWT token}", required = true,
            dataType = "string", paramType = "header")
    public ResponseEntity<?> registerBookmark(@AuthenticationPrincipal @ApiIgnore String userId, @RequestParam String servId){
        try{
            BookmarkEntity bookmark = bookmarkService.registerBookmark(userId, servId);
            return ResponseEntity.ok(bookmark);

        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBookmark(@AuthenticationPrincipal @ApiIgnore String userId, @RequestParam String bookmarkId){
        try{
            bookmarkService.deleteBookmark(bookmarkId);
            return ResponseEntity.ok(null);

        } catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


}
