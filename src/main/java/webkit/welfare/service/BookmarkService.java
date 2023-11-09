package webkit.welfare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webkit.welfare.domain.BookmarkEntity;
import webkit.welfare.domain.UserEntity;
import webkit.welfare.domain.WelfareEntity;
import webkit.welfare.repository.BookmarkRepository;
import webkit.welfare.repository.UserRepository;
import webkit.welfare.repository.WelfareRepository;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final WelfareRepository welfareRepository;


    public BookmarkEntity registerBookmark(String userId, String servId){
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        Optional<WelfareEntity> welfareEntity = welfareRepository.findById(servId);

        if(userEntity.isPresent() && welfareEntity.isPresent()){
            BookmarkEntity bookmark = BookmarkEntity.builder()
                    .user(userEntity.get())
                    .welfare(welfareEntity.get())
                    .serviceName(welfareEntity.get().getServNm())
                    .isValid(true).build();

            return bookmarkRepository.save(bookmark);
        }else {
            throw new RuntimeException();
        }
    }

    public void deleteBookmark(String bookmarkId){
        Optional<BookmarkEntity> bookmark = bookmarkRepository.findById(bookmarkId);
        if (bookmark.isPresent()){
            bookmarkRepository.delete(bookmark.get());
        }else{
            throw new RuntimeException();
        }
    }

}
