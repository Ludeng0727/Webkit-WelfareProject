package webkit.welfare.repository;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webkit.welfare.domain.BookmarkEntity;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, String> {
    List<BookmarkEntity> findByUserId(String userId);

//    List<BookmarkEntity> findByWelfareId(String welfareId);
}
