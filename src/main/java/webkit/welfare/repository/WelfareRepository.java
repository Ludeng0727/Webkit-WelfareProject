package webkit.welfare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webkit.welfare.domain.WelfareEntity;

import java.util.List;

@Repository
public interface WelfareRepository extends JpaRepository<WelfareEntity, String> {

    List<WelfareEntity> findAllByServNmContaining(String keyword);

    Boolean existsByServId(String servId);
}
