package webkit.welfare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webkit.welfare.domain.BookmarkEntity;
import webkit.welfare.domain.UserEntity;


import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);

    Boolean existsByEmail(String email);

    UserEntity findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT t.bookmarks from UserEntity t where t.id = :userId")
    List<BookmarkEntity> findBookmarksByUserId(String userId);
}
