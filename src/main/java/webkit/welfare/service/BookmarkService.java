package webkit.welfare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webkit.welfare.domain.BookmarkEntity;
import webkit.welfare.repository.BookmarkRepository;
import webkit.welfare.repository.WelfareRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    @Autowired
    private final BookmarkRepository bookmarkRepository;

    @Autowired
    private final WelfareRepository welfareRepository;

    // 북마크 생성
//    public BookmarkEntity addBookmark(BookmarkEntity bookmarkEntity, String userId, String servId) {
//
//    }

    // 회원별 북마크 정보 가져오기
    public List<BookmarkEntity> getUserBookmarkList(String userId){
        setValidate(userId);
        return bookmarkRepository.findByUserId(userId);
    }

    // 북마크와 복지정보 연결 갱신
    public void setValidate(String userId) {
        List<BookmarkEntity> bookmarkList = bookmarkRepository.findByUserId(userId);

        // 해당 유저가 북마크한 정보가 존재한다면
        if(!bookmarkList.isEmpty()) {
            // 이 때 북마크와 DB의 복지 정보가 유효한지 갱신
            // 추후 스케줄러로 모든 북마크를 한 번에 갱신하도록 바꿔보기
            for(BookmarkEntity bookmark : bookmarkList) {
                if(welfareRepository.existsByServId(bookmark.getServId())) {
                    // 해당 servId의 복지 정보가 존재한다면 북마크와 연결
                    // isValid를 true로
                    bookmark.setWelfare(welfareRepository.findById(bookmark.getServId()).orElseThrow(() -> new EntityNotFoundException("Welfare Not Found.")));
                    bookmark.setIsValid(true);
                } else {
                    // isValid를 false로
                    bookmark.setIsValid(false);
                }
                bookmarkRepository.save(bookmark);
            }
        }
    }

    // 누락항목 유효성 검사

}
