package webkit.welfare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webkit.welfare.domain.BookmarkEntity;
import webkit.welfare.repository.BookmarkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public List<BookmarkEntity> getAllBookmakrList(){
        return bookmarkRepository.findAll();
    }

}
