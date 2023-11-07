package webkit.welfare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webkit.welfare.domain.WelfareEntity;

@Repository
public interface WelfareRepository extends JpaRepository<WelfareEntity, String> {
}
