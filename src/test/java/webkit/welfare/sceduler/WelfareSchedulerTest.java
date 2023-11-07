package webkit.welfare.sceduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import webkit.welfare.repository.WelfareRepository;
import webkit.welfare.service.WelfareService;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class WelfareSchedulerTest {

    @Autowired
    private WelfareScheduler welfareScheduler;

//    @BeforeEach
//    public void setUp() {
//        ReflectionTestUtils.setField(welfareScheduler, "API_KEY",
//                "?serviceKey=cUI0v7qNq%2BI2lb7JS7tm%2F94jx1qU8OVMW6tgz9ZO9b%2FGdZUxTzPmTqsBV6KYlC8NsMxzZBxFAlwrUtELLQRNhA%3D%3D");
//    }
    @Test
    void updateWelfare() throws URISyntaxException, JsonProcessingException {
        welfareScheduler.updateWelfare();

    }
}